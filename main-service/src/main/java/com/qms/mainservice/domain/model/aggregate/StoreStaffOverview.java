package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.entity.StoreStaff;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.AggregateRoot;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreStaffOverview extends AggregateRoot<StoreId> {
    // 店舗スタッフ一覧
    private List<StoreStaff> storeStaffs;

    // 活動スタッフ一覧
    private List<ActiveStaff> activeStaffs;

    // デフォルトコンストラクタ
    private StoreStaffOverview() {
    }

    // 店舗スタッフを追加する
    public void addStoreStaff(StaffId staffId) {
        StoreStaff storeStaff = StoreStaff.create(staffId, id);
        storeStaffs.add(storeStaff);
    }

    // 活動スタッフを追加する
    public void addActiveStaff(StaffId staffId) {
        SortOrder sortOrder = SortOrder.of(activeStaffs.size() + 1);
        ActiveStaff activeStaff = ActiveStaff.create(id, staffId, sortOrder);
        activeStaffs.add(activeStaff);
    }

    // 活動スタッフを削除する
    public void removeActiveStaff(ActiveStaffKey activeStaffKey) {
        activeStaffs.removeIf(activeStaff -> activeStaff.getKey()
                .equals(activeStaffKey));
        // 活動スタッフのソート順を再設定する
        resetSortOrder();
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

    // 活動スタッフのソート順を再設定する
    private void resetSortOrder() {
        for (int i = 0; i < activeStaffs.size(); i++) {
            ActiveStaff activeStaff = activeStaffs.get(i);
            activeStaff.setSortOrder(SortOrder.of(i + 1));
        }
    }

    // DBから取得したレコードをActiveStaffsに変換する
    public static StoreStaffOverview reconstruct(
            StoreId storeId,
            List<StoreStaff> storeStaffList,
            List<ActiveStaff> activeStaffList
    ) {
        StoreStaffOverview staffOverview = new StoreStaffOverview();
        staffOverview.id = storeId;
        staffOverview.storeStaffs = storeStaffList;
        staffOverview.activeStaffs = activeStaffList;
        return staffOverview;
    }


}
