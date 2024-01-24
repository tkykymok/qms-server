package com.qms.mainservice.infrastructure.repository.store;

import com.qms.mainservice.domain.model.aggregate.StoreAggregate;
import com.qms.mainservice.domain.model.entity.StoreBusinessHour;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.mainservice.domain.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

import static com.qms.mainservice.infrastructure.jooq.Tables.STORES;
import static com.qms.mainservice.infrastructure.jooq.Tables.STORE_BUSINESS_HOURS;

@Repository
@RequiredArgsConstructor
public class JooqStoreRepository implements StoreRepository {

    private final DSLContext dsl;

    @Override
    public List<StoreAggregate> findStoreDetailsByLocation(Point point, int distance) {

        // 店舗IDをキーに、店舗毎の営業時間をMapに格納する
        Map<Long, Result<Record>> storeBusinessHourMap = dsl.select()
                .from(STORE_BUSINESS_HOURS)
                .fetch()
                .intoGroups(STORE_BUSINESS_HOURS.STORE_ID);


        // 店舗一覧を取得する
        Result<Record> storeRecords = dsl.select()
                .from(STORES)
                .where(String.format("ST_Distance_Sphere(POINT(%f, %f), POINT(longitude, latitude)) <= %d",
                        point.getX(), point.getY(), distance))
                .fetch();

        // 店舗営業時間一覧を取得する
        return storeRecords.map(record -> recordToStoreAggregate(record, storeBusinessHourMap));
    }

    private StoreAggregate recordToStoreAggregate(Record record, Map<Long, Result<Record>> storeBusinessHourMap) {
        return StoreAggregate.reconstruct(
                StoreId.of(record.get(STORES.ID)),
                CompanyId.of(record.get(STORES.COMPANY_ID)),
                StoreName.of(record.get(STORES.STORE_NAME)),
                PostalCode.of(record.get(STORES.POSTAL_CODE)),
                Address.of(record.get(STORES.ADDRESS)),
                Latitude.of(record.get(STORES.LATITUDE).doubleValue()),
                Longitude.of(record.get(STORES.LONGITUDE).doubleValue()),
                PhoneNumber.of(record.get(STORES.PHONE_NUMBER)),
                HomePageUrl.of(record.get(STORES.HOME_PAGE_URL)),
                storeBusinessHourMap.get(record.get(STORES.ID))
                        .map(this::recordToStoreBusinessHour)
        );
    }

    private StoreBusinessHour recordToStoreBusinessHour(Record record) {
        return StoreBusinessHour.reconstruct(
                StoreId.of(record.get(STORE_BUSINESS_HOURS.STORE_ID)),
                DayOfWeek.of(record.get(STORE_BUSINESS_HOURS.DAY_OF_WEEK)),
                OpenTime.of(record.get(STORE_BUSINESS_HOURS.OPEN_TIME)),
                CloseTime.of(record.get(STORE_BUSINESS_HOURS.CLOSE_TIME)),
                Flag.fromValue(record.get(STORE_BUSINESS_HOURS.CLOSED))
        );
    }


}
