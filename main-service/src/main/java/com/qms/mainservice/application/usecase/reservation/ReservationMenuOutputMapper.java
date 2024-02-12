package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.entity.ReservationMenu;

public class ReservationMenuOutputMapper {

    public static ReservationMenuOutput modelToReservationMenuOutput(ReservationMenu reservationMenu) {
        return ReservationMenuOutput.builder()
                .reservationId(reservationMenu.getKey().reservationId()) // 予約ID
                .storeId(reservationMenu.getKey().storeId()) // 店舗ID
                .storeMenuId(reservationMenu.getKey().storeMenuId()) // 店舗メニューID
                .menuName(reservationMenu.getMenu().getMenuName()) // メニュー名
                .price(reservationMenu.getMenu().getPrice()) // 価格
                .time(reservationMenu.getMenu().getTime()) // 所要時間
                .tagColor(reservationMenu.getMenu().getTagColor()) // タグ色
                .build();
    }
}
