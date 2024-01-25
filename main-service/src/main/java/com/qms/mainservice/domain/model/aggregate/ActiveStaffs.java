package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.AggregateRoot;

import java.util.List;

public class ActiveStaffs extends AggregateRoot<StoreId> {
    // 活動スタッフ一覧
    private List<ActiveStaff> activeStaffs;

    private ActiveStaffs() {
    }

    // 活動スタッフを追加する
    public void add(ActiveStaff activeStaff) {
        // 活動スタッフのソート順を設定する
        SortOrder sortOrder = SortOrder.of(activeStaffs.size() + 1);
        activeStaff.setSortOrder(sortOrder);
        activeStaffs.add(activeStaff);
    }

    // 活動スタッフを削除する
    public void remove(ActiveStaffKey activeStaffKey) {
        activeStaffs.removeIf(activeStaff -> activeStaff.getKey()
                .equals(activeStaffKey));
    }

    // 休憩時間を設定する
    public void setBreakTime(
            ActiveStaffKey activeStaffKey,
            BreakStartTime breakStartTime,
            BreakEndTime breakEndTime
    ) {
        activeStaffs.stream()
                .filter(activeStaff -> activeStaff.getKey().
                        equals(activeStaffKey))
                .findFirst()
                .ifPresent(activeStaff -> activeStaff.setBreakTime(
                        breakStartTime,
                        breakEndTime
                ));
    }

    // DBから取得したレコードをActiveStaffsに変換する
    public static ActiveStaffs reconstruct(
            StoreId storeId,
            List<ActiveStaff> activeStaffList
    ) {
        ActiveStaffs activeStaffs = new ActiveStaffs();
        activeStaffs.id = storeId;
        activeStaffs.activeStaffs = activeStaffList;
        return activeStaffs;
    }

}
