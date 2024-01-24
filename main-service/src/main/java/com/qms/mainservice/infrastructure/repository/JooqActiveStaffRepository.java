package com.qms.mainservice.infrastructure.repository;

import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.mainservice.domain.repository.ActiveStaffRepository;
import static com.qms.mainservice.infrastructure.jooq.Tables.*;
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
        Result<Record> storeRecords = dsl.select()
                .from(ACTIVE_STAFFS)
                .where(ACTIVE_STAFFS.STORE_ID.eq(storeId.value()))
                .fetch();

        return storeRecords.map(this::recordToActiveStaff);
    }

    // RecordからActiveStaffを生成する
    private ActiveStaff recordToActiveStaff(Record record) {
        return ActiveStaff.reconstruct(
                StoreId.of(record.get(ACTIVE_STAFFS.STORE_ID)),
                StaffId.of(record.get(ACTIVE_STAFFS.STAFF_ID)),
                SortOrder.of(record.get(ACTIVE_STAFFS.SORT_ORDER)),
                BreakStartDateTime.of(record.get(ACTIVE_STAFFS.BREAK_START_DATETIME)),
                BreakEndDateTime.of(record.get(ACTIVE_STAFFS.BREAK_END_DATETIME)),
                ReservationId.of(record.get(ACTIVE_STAFFS.RESERVATION_ID))
        );
    }



}
