package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.CompositeKeyBaseEntity;
import com.qms.shared.domain.model.valueobject.TrackingInfo;
import lombok.Getter;

@Getter
public class Menu extends CompositeKeyBaseEntity<MenuKey> {

    private MenuName menuName;
    private Price price;
    private Time time;
    private Flag disabled;

    private Menu() {
    }

    // DBから取得したデータをドメインオブジェクトに変換する
    public static Menu reconstruct(
            StoreId storeId,
            StoreMenuId storeMenuId,
            MenuName menuName,
            Price price,
            Time time,
            Flag disabled,
            TrackingInfo trackingInfo
    ) {
        Menu menu = new Menu();
        menu.key = MenuKey.of(storeId, storeMenuId);
        menu.menuName = menuName;
        menu.price = price;
        menu.time = time;
        menu.disabled = disabled;
        menu.trackingInfo = trackingInfo;
        return menu;
    }

}
