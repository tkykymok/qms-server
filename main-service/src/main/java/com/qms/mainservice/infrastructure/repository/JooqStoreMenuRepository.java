package com.qms.mainservice.infrastructure.repository;

import com.qms.mainservice.domain.model.entity.Menu;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.repository.StoreMenuRepository;
import com.qms.mainservice.infrastructure.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.qms.mainservice.infrastructure.jooq.Tables.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JooqStoreMenuRepository implements StoreMenuRepository {

    private final DSLContext dsl;

    @Override
    public List<Menu> findAllByStoreId(StoreId storeId) {
        // 店舗IDに紐づく店舗メニュー一覧を取得する
        Result<Record> menuRecords = dsl.select()
                .from(MENUS)
                .where(MENUS.STORE_ID.eq(storeId.value()))
                .fetch();

        return menuRecords.map(MenuMapper::recordToMenu);
    }
}
