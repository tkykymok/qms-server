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
    public void addStoreStaff(StoreStaff storeStaff) {
        storeStaffs.add(storeStaff);
    }

    // 店舗スタッフを削除する
    public void removeStoreStaff(StoreStaffKey storeStaffKey) {
        storeStaffs.removeIf(storestaff -> storestaff.getKey()
                .equals(storeStaffKey));
    }

    // 活動スタッフを追加する
    public void addActiveStaff(ActiveStaff activeStaff) {
        // 活動スタッフのソート順を設定する
        SortOrder sortOrder = SortOrder.of(activeStaffs.size() + 1);
        activeStaff.setSortOrder(sortOrder);
        activeStaffs.add(activeStaff);
    }

    // 活動スタッフを削除する
    public void removeActiveStaff(ActiveStaffKey activeStaffKey) {
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

    // 店舗IDとスタッフIDを指定して該当スタッフが活動中かどうかを判定する
    public Flag getIsActive(StoreId storeId, StaffId staffId) {
        var key = ActiveStaffKey.of(storeId, staffId);
        return activeStaffs.stream()
                .filter(activeStaff -> activeStaff.getKey().equals(key))
                .findFirst()
                .map(activeStaff -> Flag.of(true))
                .orElse(Flag.of(false));
    }

    // DBから取得したレコードをActiveStaffsに変換する
    public static StoreStaffOverview reconstruct(
            StoreId storeId,
            List<StoreStaff> staffList,
            List<ActiveStaff> activeStaffList
    ) {
        StoreStaffOverview activeStaffs = new StoreStaffOverview();
        activeStaffs.id = storeId;
        activeStaffs.storeStaffs = staffList;
        activeStaffs.activeStaffs = activeStaffList;
        return activeStaffs;
    }


}