package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.ReservationMenuKey;
import com.qms.mainservice.domain.model.valueobject.Time;
import com.qms.shared.domain.model.CompositeKeyBaseEntity;
import lombok.Getter;

@Getter
public class ReservationMenu extends CompositeKeyBaseEntity<ReservationMenuKey> {

    private Time time;

    private ReservationMenu() {
        super();
    }

    // DBから取得したデータをドメインオブジェクトに変換する
    public static ReservationMenu reconstruct(ReservationMenuKey key, Time time) {
        ReservationMenu reservationMenu = new ReservationMenu();
        reservationMenu.key = key;
        reservationMenu.time = time;
        return reservationMenu;
    }

    /**
     * 予約メニューを作成する
     *
     * @param menus メニュー
     * @return 予約メニュー
     */
    public static ReservationMenu create(Menu menus) {
        ReservationMenu reservationMenu = new ReservationMenu();
        reservationMenu.time = menus.getTime();
        return reservationMenu;
    }




}
