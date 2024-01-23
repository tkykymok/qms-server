package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.entity.StoreBusinessHour;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.AggregateRoot;

import java.util.List;

public class StoreAggregate extends AggregateRoot<StoreId> {

    // 店舗
    private CompanyId companyId; // 企業ID
    private StoreName storeName; // 店舗名
    private PostalCode postalCode; // 郵便番号
    private Address address; // 住所
    private Location location; // 店舗位置
    private PhoneNumber phoneNumber; // 電話番号
    private HomePageUrl homePageUrl; // ホームページURL

    // 店舗営業時間List
    private List<StoreBusinessHour> storeBusinessHours;

    private StoreAggregate() {}


    // DBから取得したデータをドメインオブジェクトに変換する
    public static StoreAggregate reconstruct(
            StoreId storeId,
            CompanyId companyId,
            StoreName storeName,
            PostalCode postalCode,
            Address address,
            Location location,
            PhoneNumber phoneNumber,
            HomePageUrl homePageUrl,
            List<StoreBusinessHour> storeBusinessHours
    ) {
        StoreAggregate storeAggregate = new StoreAggregate();
        storeAggregate.id = storeId;
        storeAggregate.companyId = companyId;
        storeAggregate.storeName = storeName;
        storeAggregate.postalCode = postalCode;
        storeAggregate.address = address;
        storeAggregate.location = location;
        storeAggregate.phoneNumber = phoneNumber;
        storeAggregate.homePageUrl = homePageUrl;
        storeAggregate.storeBusinessHours = storeBusinessHours;
        return storeAggregate;
    }


}
