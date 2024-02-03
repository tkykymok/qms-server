package com.qms.mainservice.infrastructure.repository;

import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.mainservice.domain.repository.ActiveStaffRepository;
import static com.qms.mainservice.infrastructure.jooq.Tables.*;

import com.qms.mainservice.infrastructure.mapper.StaffMapper;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JooqActiveStaffRepository implements ActiveStaffRepository {

    private final DSLContext dsl;

    @Override
    public List<ActiveStaff> findAllByStoreId(StoreId storeId) {
        // 店舗IDに紐づく活動スタッフ一覧を取得する
        Result<Record> activeStaffRecords = dsl.select()
                .from(ACTIVE_STAFFS)
                .innerJoin(STAFFS).on(ACTIVE_STAFFS.STAFF_ID.eq(STAFFS.ID))
                .where(ACTIVE_STAFFS.STORE_ID.eq(storeId.value()))
                .fetch();

        return activeStaffRecords.map(StaffMapper::recordToActiveStaff);
    }

}
