package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.CompositeKeyBaseEntity;
import com.qms.shared.domain.model.valueobject.TrackingInfo;
import lombok.Getter;

@Getter
public class ReservationMenu extends CompositeKeyBaseEntity<ReservationMenuKey> {

    private Menu menu;

    private ReservationMenu() {
        super();
    }

    // DBから取得したデータをドメインオブジェクトに変換する
    public static ReservationMenu reconstruct(
            ReservationId reservationId,
            StoreId storeId,
            StoreMenuId storeMenuId,
            TrackingInfo trackingInfo,
            Menu menu
    ) {
        ReservationMenu reservationMenu = new ReservationMenu();
        reservationMenu.key = ReservationMenuKey.of(reservationId, storeId, storeMenuId);
        reservationMenu.trackingInfo = trackingInfo;
        reservationMenu.menu = menu;
        return reservationMenu;
    }


    /**
     * 予約メニューを作成する
     *
     * @param reservationId 予約ID
     * @param storeId       店舗ID
     * @param storeMenuId   店舗メニューID
     * @return 予約メニュー
     */
    public static ReservationMenu create(
            ReservationId reservationId,
            StoreId storeId,
            StoreMenuId storeMenuId) {
        ReservationMenu reservationMenu = new ReservationMenu();
        reservationMenu.key = ReservationMenuKey.of(reservationId, storeId, storeMenuId);
        reservationMenu.trackingInfo = TrackingInfo.create();
        return reservationMenu;
    }

}
