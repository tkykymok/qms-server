package com.qms.mainservice.infrastructure.mapper;

import com.qms.mainservice.domain.model.aggregate.Store;
import com.qms.mainservice.domain.model.entity.StoreBusinessHour;
import com.qms.mainservice.domain.model.valueobject.*;
import org.jooq.Record;
import org.jooq.Result;

import java.time.DayOfWeek;
import java.util.Map;

import static com.qms.mainservice.infrastructure.jooq.Tables.STORES;
import static com.qms.mainservice.infrastructure.jooq.Tables.STORE_BUSINESS_HOURS;

public class StoreMapper {

    public static Store recordToStore(Record record, Map<Long, Result<Record>> storeBusinessHourMap) {
        return Store.reconstruct(
                StoreId.of(record.get(STORES.ID)),
                CompanyId.of(record.get(STORES.COMPANY_ID)),
                StoreName.of(record.get(STORES.STORE_NAME)),
                PostalCode.of(record.get(STORES.POSTAL_CODE)),
                Address.of(record.get(STORES.ADDRESS)),
                Latitude.of(record.get(STORES.LATITUDE).doubleValue()),
                Longitude.of(record.get(STORES.LONGITUDE).doubleValue()),
                PhoneNumber.of(record.get(STORES.PHONE_NUMBER)),
                HomePageUrl.of(record.get(STORES.HOME_PAGE_URL)),
                storeBusinessHourMap.isEmpty()
                        ? null
                        : storeBusinessHourMap.get(record.get(STORES.ID))
                        .map(StoreMapper::recordToStoreBusinessHour)
        );
    }

    private static StoreBusinessHour recordToStoreBusinessHour(Record record) {
        return StoreBusinessHour.reconstruct(
                StoreId.of(record.get(STORE_BUSINESS_HOURS.STORE_ID)),
                DayOfWeek.of(record.get(STORE_BUSINESS_HOURS.DAY_OF_WEEK)),
                OpenTime.of(record.get(STORE_BUSINESS_HOURS.OPEN_TIME)),
                CloseTime.of(record.get(STORE_BUSINESS_HOURS.CLOSE_TIME)),
                Flag.fromValue(record.get(STORE_BUSINESS_HOURS.CLOSED))
        );
    }

}
