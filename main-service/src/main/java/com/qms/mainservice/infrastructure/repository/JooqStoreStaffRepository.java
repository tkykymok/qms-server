package com.qms.mainservice.infrastructure.repository;

import com.qms.mainservice.domain.model.aggregate.StoreStaffOverview;
import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.entity.StoreStaff;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.repository.StoreStaffRepository;
import com.qms.mainservice.infrastructure.mapper.StaffMapper;
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
public class JooqStoreStaffRepository implements StoreStaffRepository {

    private final DSLContext dsl;

    @Override
    public List<StoreStaff> findAllByStoreId(StoreId storeId) {
        // 店舗IDに紐づく店舗スタッフ一覧を取得する
        Result<Record> storeStaffRecords = dsl.select()
                .from(STORE_STAFFS)
                .innerJoin(STAFFS).on(STORE_STAFFS.STAFF_ID.eq(STAFFS.ID))
                .where(STORE_STAFFS.STORE_ID.eq(storeId.value()))
                .fetch();

        return storeStaffRecords.map(StaffMapper::recordToStoreStaff);
    }

    @Override
    public void deleteAndInsertActiveStaff(StoreStaffOverview storeStaffOverview) {
        // 活動中スタッフをDELETEする
        dsl.deleteFrom(ACTIVE_STAFFS)
                .where(ACTIVE_STAFFS.STORE_ID.eq(storeStaffOverview.getId().value()))
                .execute();

        // 活動中スタッフをINSERTする
        List<ActiveStaff> activeStaffs = storeStaffOverview.getActiveStaffs();
        dsl.batchInsert(activeStaffs.stream()
                .map(StaffMapper::activeStaffToRecord)
                .toList())
                .execute();
    }
}
