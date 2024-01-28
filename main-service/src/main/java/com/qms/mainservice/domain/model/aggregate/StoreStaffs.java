package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.entity.StoreStaff;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.model.valueobject.StoreStaffKey;
import com.qms.shared.domain.model.AggregateRoot;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreStaffs extends AggregateRoot<StoreId> {
    // 店舗スタッフ一覧
    private List<StoreStaff> storeStaffs;

    // デフォルトコンストラクタ
    private StoreStaffs() {
    }

    // 活動スタッフを追加する
    public void add(StoreStaff storeStaff) {
        storeStaffs.add(storeStaff);
    }

    // 店舗スタッフを削除する
    public void remove(StoreStaffKey storeStaffKey) {
        storeStaffs.removeIf(storestaff -> storestaff.getKey()
                .equals(storeStaffKey));
    }


    // DBから取得したレコードをActiveStaffsに変換する
    public static StoreStaffs reconstruct(
            StoreId storeId,
            List<StoreStaff> staffList
    ) {
        StoreStaffs staffs = new StoreStaffs();
        staffs.id = storeId;
        staffs.storeStaffs = staffList;
        return staffs;
    }


}
