package com.qms.mainservice.infrastructure.repository;

import com.qms.mainservice.domain.model.entity.Menu;
import com.qms.mainservice.domain.model.valueobject.MenuKey;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.model.valueobject.StoreMenuId;
import com.qms.mainservice.domain.repository.StoreMenusRepository;
import com.qms.mainservice.infrastructure.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.stream.Collectors;

import static com.qms.mainservice.infrastructure.jooq.Tables.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JooqStoreMenusRepository implements StoreMenusRepository {

    private final DSLContext dsl;

    @Override
    public Map<StoreMenuId, Menu> findAllByStoreId(StoreId storeId) {
        // 店舗IDに紐づく店舗メニュー一覧を取得する
        Map<Integer, Result<Record>> menuRecordsMap = dsl.select()
                .from(MENUS)
                .where(MENUS.STORE_ID.eq(storeId.value()))
                .fetch()
                .intoGroups(MENUS.STORE_MENU_ID);

        return menuRecordsMap.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                e -> StoreMenuId.of(e.getKey()),
                                e -> MenuMapper.recordToMenu(e.getValue().getFirst())
                        )
                );
    }
}
