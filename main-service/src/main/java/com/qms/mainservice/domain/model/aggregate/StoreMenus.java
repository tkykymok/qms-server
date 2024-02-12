package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.entity.Menu;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.shared.domain.model.AggregateRoot;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StoreMenus extends AggregateRoot<StoreId> {
    // メニューList
    private List<Menu> menus;

    private StoreMenus() {
    }

    // DBから取得したデータをドメインオブジェクトに変換する
    public static StoreMenus reconstruct(
            StoreId storeId,
            List<Menu> menus
    ) {
        StoreMenus storeMenus = new StoreMenus();
        storeMenus.id = storeId;
        storeMenus.menus = new ArrayList<>(menus);
        return storeMenus;
    }

}
