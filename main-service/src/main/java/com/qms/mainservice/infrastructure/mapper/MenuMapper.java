package com.qms.mainservice.infrastructure.mapper;

import com.qms.mainservice.domain.model.entity.Menu;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.valueobject.TrackingInfo;
import com.qms.shared.domain.model.valueobject.UserType;
import org.jooq.Record;

import java.math.BigDecimal;

import static com.qms.mainservice.infrastructure.jooq.Tables.MENUS;

public class MenuMapper {
    public static Menu recordToMenu(Record record) {
        return Menu.reconstruct(
                StoreId.of(record.get(MENUS.STORE_ID)),
                StoreMenuId.of(record.get(MENUS.STORE_MENU_ID)),
                MenuName.of(record.get(MENUS.MENU_NAME)),
                Price.of(BigDecimal.valueOf(record.get(MENUS.PRICE))),
                Time.of(record.get(MENUS.TIME)),
                Flag.fromValue(record.get(MENUS.DISABLED)),
                TrackingInfo.reconstruct(
                        record.get(MENUS.CREATED_AT),
                        record.get(MENUS.UPDATED_AT),
                        record.get(MENUS.CREATED_BY),
                        UserType.fromValue(record.get(MENUS.CREATED_BY_TYPE)),
                        record.get(MENUS.UPDATED_BY),
                        UserType.fromValue(record.get(MENUS.UPDATED_BY_TYPE))
                )
        );
    }
}
