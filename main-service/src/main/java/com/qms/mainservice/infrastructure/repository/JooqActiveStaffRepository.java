package com.qms.mainservice.infrastructure.repository;

import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.valueobject.ReservationStatus;
import com.qms.mainservice.domain.model.valueobject.ReservedDate;
import com.qms.mainservice.domain.model.valueobject.StaffId;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.repository.ActiveStaffRepository;
import com.qms.mainservice.infrastructure.mapper.StaffMapper;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.qms.mainservice.infrastructure.jooq.Tables.*;

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

    /**
     * 店舗IDとスタッフIDを指定して、活動スタッフが削除可能か判定する
     * @param storeId 店舗ID
     * @param staffId スタッフID
     * @return 削除可能な場合true
     */
    @Override
    public boolean isRemovable(StoreId storeId, StaffId staffId) {
        // 対応中の予約が存在しない場合trueを返す
        return !dsl.fetchExists(
                ACTIVE_STAFFS.innerJoin(RESERVATIONS)
                        .on(ACTIVE_STAFFS.STAFF_ID.eq(RESERVATIONS.STAFF_ID),
                                ACTIVE_STAFFS.STORE_ID.eq(RESERVATIONS.STORE_ID),
                                RESERVATIONS.RESERVED_DATE.eq(ReservedDate.now().value()),
                                RESERVATIONS.STATUS.eq(ReservationStatus.IN_PROGRESS.getValue())
                        )
        );
    }
}
