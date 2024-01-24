package com.qms.mainservice.application.usecase.store;

import com.qms.mainservice.domain.model.aggregate.Store;
import com.qms.mainservice.infrastructure.repository.JooqStoreRepository;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FetchStoresUsecase extends Usecase<FetchStoresInput, FetchStoresOutput> {

    private final JooqStoreRepository jooqStoreRepository;

    @Override
    public FetchStoresOutput execute(FetchStoresInput input) {
        // 座標をPointに変換する
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(input.longitude(), input.latitude()); // 経度、緯度の順番
        Point point = geometryFactory.createPoint(coordinate);

        // 指定の距離内に存在する店舗一覧を取得する
        List<Store> stores = jooqStoreRepository.findStoreDetailsByLocation(point, 2000);

        // 店舗一覧が存在しない場合は空のListを返す
        if (stores.isEmpty()) {
            return FetchStoresOutput.builder()
                    .stores(List.of())
                    .build();
        }

        // 店舗一覧をOutputに詰め替える
        List<StoreOutput> storeOutputs = stores.stream()
                .map(store -> StoreOutput.builder()
                        .storeId(store.getId())
                        .companyId(store.getCompanyId())
                        .storeName(store.getStoreName())
                        .postalCode(store.getPostalCode())
                        .address(store.getAddress())
                        .latitude(store.getLatitude())
                        .longitude(store.getLongitude())
                        .phoneNumber(store.getPhoneNumber())
                        .homePageUrl(store.getHomePageUrl())
                        .isClosed(store.isClosed())
                        .weekdayHours(store.getWeekdayHours())
                        .holidayHours(store.getHolidayHours())
                        .regularHolidays(store.getRegularHolidays())
                        .build())
                .toList();

        return FetchStoresOutput.builder()
                .stores(storeOutputs)
                .build();
    }
}
