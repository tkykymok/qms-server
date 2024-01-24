package com.qms.mainservice.application.usecase.store;

import com.qms.mainservice.domain.model.aggregate.StoreAggregate;
import com.qms.mainservice.infrastructure.repository.store.JooqStoreRepository;
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
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(input.longitude(), input.latitude()); // 経度、緯度の順番
        Point point = geometryFactory.createPoint(coordinate);
        List<StoreAggregate> storeAggregates = jooqStoreRepository.findStoreDetailsByLocation(point, 2000);

        if (storeAggregates.isEmpty()) {
            return FetchStoresOutput.builder()
                    .stores(List.of())
                    .build();
        }

        List<StoreOutput> storeOutputs = storeAggregates.stream()
                .map(storeAggregate -> StoreOutput.builder()
                        .storeId(storeAggregate.getId())
                        .companyId(storeAggregate.getCompanyId())
                        .storeName(storeAggregate.getStoreName())
                        .postalCode(storeAggregate.getPostalCode())
                        .address(storeAggregate.getAddress())
                        .latitude(storeAggregate.getLatitude())
                        .longitude(storeAggregate.getLongitude())
                        .phoneNumber(storeAggregate.getPhoneNumber())
                        .homePageUrl(storeAggregate.getHomePageUrl())
                        .isClosed(storeAggregate.isClosed())
                        .weekdayHours(storeAggregate.getWeekdayHours())
                        .holidayHours(storeAggregate.getHolidayHours())
                        .regularHolidays(storeAggregate.getRegularHolidays())
                        .build())
                .toList();

        return FetchStoresOutput.builder()
                .stores(storeOutputs)
                .build();
    }
}
