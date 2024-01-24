package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.entity.StoreBusinessHour;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.AggregateRoot;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Store extends AggregateRoot<StoreId> {

    // 店舗
    private CompanyId companyId; // 企業ID
    private StoreName storeName; // 店舗名
    private PostalCode postalCode; // 郵便番号
    private Address address; // 住所
    private Latitude latitude; // 緯度
    private Longitude longitude; // 経度
    private PhoneNumber phoneNumber; // 電話番号
    private HomePageUrl homePageUrl; // ホームページURL

    // 店舗営業時間List
    private List<StoreBusinessHour> storeBusinessHours;

    private Store() {
    }

    // DBから取得したデータをドメインオブジェクトに変換する
    public static Store reconstruct(
            StoreId storeId,
            CompanyId companyId,
            StoreName storeName,
            PostalCode postalCode,
            Address address,
            Latitude latitude,
            Longitude longitude,
            PhoneNumber phoneNumber,
            HomePageUrl homePageUrl,
            List<StoreBusinessHour> storeBusinessHours
    ) {
        Store store = new Store();
        store.id = storeId;
        store.companyId = companyId;
        store.storeName = storeName;
        store.postalCode = postalCode;
        store.address = address;
        store.latitude = latitude;
        store.longitude = longitude;
        store.phoneNumber = phoneNumber;
        store.homePageUrl = homePageUrl;
        store.storeBusinessHours = storeBusinessHours;
        return store;
    }

    // 店舗営業時間Listから平日の営業時間を取得する
    public String getWeekdayHours() {
        return storeBusinessHours.stream()
                .filter(hour -> hour.getKey().dayOfWeek().getValue() >= DayOfWeek.MONDAY.getValue()
                        && hour.getKey().dayOfWeek().getValue() <= DayOfWeek.FRIDAY.getValue())
                .filter(hour -> !hour.getClosed().value())
                .map(hour -> hour.getOpenTime().value() + "〜" + hour.getCloseTime().value())
                .findFirst()
                .orElse("Closed");
    }

    // 店舗営業時間Listから土日祝の営業時間を取得する
    public String getHolidayHours() {
        return storeBusinessHours.stream()
                .filter(hour -> hour.getKey().dayOfWeek().getValue() == DayOfWeek.SATURDAY.getValue()
                        || hour.getKey().dayOfWeek().getValue() == DayOfWeek.SUNDAY.getValue())
                .filter(hour -> !hour.getClosed().value())
                .map(hour -> hour.getOpenTime().value() + "〜" + hour.getCloseTime().value())
                .findFirst()
                .orElse("Closed");
    }

    // 店舗営業時間Listから定休日一覧を取得する
    public List<DayOfWeek> getRegularHolidays() {
        return storeBusinessHours.stream()
                .filter(hour -> hour.getClosed().value())
                .map(hour -> hour.getKey().dayOfWeek())
                .collect(Collectors.toList());
    }

    // 今日の日付から定休日かどうかを判定する
    public Flag isClosed() {
        return storeBusinessHours.stream()
                .filter(hour -> hour.getKey().dayOfWeek().equals(LocalDate.now().getDayOfWeek()))
                .map(StoreBusinessHour::getClosed)
                .findFirst()
                .orElse(Flag.OFF());

    }

}
