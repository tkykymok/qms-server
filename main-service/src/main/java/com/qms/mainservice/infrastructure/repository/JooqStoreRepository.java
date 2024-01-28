package com.qms.mainservice.infrastructure.repository;

import com.qms.mainservice.domain.model.aggregate.Store;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.repository.StoreRepository;
import com.qms.mainservice.infrastructure.mapper.StoreMapper;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.qms.mainservice.infrastructure.jooq.Tables.STORES;
import static com.qms.mainservice.infrastructure.jooq.Tables.STORE_BUSINESS_HOURS;

@Repository
@RequiredArgsConstructor
public class JooqStoreRepository implements StoreRepository {

    private final DSLContext dsl;

    @Override
    public List<Store> findStoreDetailsByLocation(Point point, int distance) {

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
        return storeRecords.map(record -> StoreMapper.recordToStore(record, storeBusinessHourMap));
    }

    @Override
    public Store findById(StoreId storeId) {
        // 店舗IDに紐づく店舗情報を取得する
        Record storeRecord = dsl.select()
                .from(STORES)
                .where(STORES.ID.eq(storeId.value()))
                .fetchOne();
        if (storeRecord == null) {
            return null;
        }
        return StoreMapper.recordToStore(storeRecord, Map.of());
    }

}
