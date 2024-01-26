package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.entity.StoreStaff;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.AggregateRoot;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreStaffOverview extends AggregateRoot<StoreId> {
    // 店舗
    private CompanyId companyId; // 企業ID

    // 店舗スタッフ一覧
    private List<StoreStaff> storeStaffs;

    // 活動スタッフ一覧
    private List<ActiveStaff> activeStaffs;

    // デフォルトコンストラクタ
    private StoreStaffOverview() {
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

//    // DBから取得したレコードをActiveStaffsに変換する
//    public static ActiveStaffs reconstruct(
//            StoreId storeId,
//            List<ActiveStaff> activeStaffList
//    ) {
//        ActiveStaffs activeStaffs = new ActiveStaffs();
//        activeStaffs.id = storeId;
//        activeStaffs.activeStaffs = activeStaffList;
//        return activeStaffs;
//    }


}
