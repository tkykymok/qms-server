package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.entity.StoreStaff;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.exception.DomainException;
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

    // 店舗スタッフの活動状態を切り替える
    public void toggleActive(StaffId staffId, Flag afterIsActive) {
        // StoreStaffKeyを指定して、該当スタッフの存在を確認する
        if (storeStaffs.stream()
                .noneMatch(storeStaff -> storeStaff.getKey().equals(StoreStaffKey.of(staffId, id)))
        ) {
            throw new DomainException("指定されたスタッフは存在しません");
        }

        // 変更後のisActiveがONの場合、活動中スタッフに追加する
        if (afterIsActive.isON()) {
            // 活動中スタッフに既に存在する場合はエラー
            if (activeStaffs.stream()
                    .anyMatch(activeStaff -> activeStaff.getKey().equals(ActiveStaffKey.of(id, staffId)))
            ) {
                throw new DomainException("指定されたスタッフは既に活動中です");
            }

            // 活動中スタッフを追加する
            activeStaffs.add(ActiveStaff.create(
                    id,
                    staffId,
                    SortOrder.of(activeStaffs.size() + 1)
            ));
        } else {
            // 活動中スタッフを削除する
            activeStaffs.removeIf(activeStaff -> activeStaff.getKey()
                    .equals(ActiveStaffKey.of(id, staffId)));
            // 並び順を再設定する
            sortActiveStaffs(activeStaffs.stream()
                    .map(ActiveStaff::getKey)
                    .map(ActiveStaffKey::staffId)
                    .toList());
        }
    }

    public void addStoreStaff(StoreStaff storeStaff) {
        storeStaffs.add(storeStaff);
    }

    // 店舗スタッフを削除する
    public void removeStoreStaff(StoreStaffKey storeStaffKey) {
        storeStaffs.removeIf(storestaff -> storestaff.getKey()
                .equals(storeStaffKey));
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

    // 活動中スタッフの並び順を変更する
    public void sortActiveStaffs(List<StaffId> staffIds) {
        activeStaffs.forEach(activeStaff -> {
            var sortOrder = staffIds.indexOf(activeStaff.getKey().staffId());
            activeStaff.setSortOrder(SortOrder.of(sortOrder + 1)); // 並び順
            activeStaff.getTrackingInfo().setUpdateInfo(); // 更新情報
        });
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

    // 店舗IDとスタッフIDを指定して該当スタッフの並び順を取得する
    public SortOrder getSortOrder(StoreId storeId, StaffId staffId) {
        var key = ActiveStaffKey.of(storeId, staffId);
        return activeStaffs.stream()
                .filter(activeStaff -> activeStaff.getKey().equals(key))
                .findFirst()
                .map(ActiveStaff::getSortOrder)
                .orElse(null);
    }

    // 店舗IDとスタッフIDを指定して該当スタッフの休憩開始時間を取得する
    public BreakStartTime getBreakStartTime(StoreId storeId, StaffId staffId) {
        var key = ActiveStaffKey.of(storeId, staffId);
        return activeStaffs.stream()
                .filter(activeStaff -> activeStaff.getKey().equals(key))
                .findFirst()
                .map(ActiveStaff::getBreakStartTime)
                .orElse(null);
    }

    // 店舗IDとスタッフIDを指定して該当スタッフの休憩終了時間を取得する
    public BreakEndTime getBreakEndTime(StoreId storeId, StaffId staffId) {
        var key = ActiveStaffKey.of(storeId, staffId);
        return activeStaffs.stream()
                .filter(activeStaff -> activeStaff.getKey().equals(key))
                .findFirst()
                .map(ActiveStaff::getBreakEndTime)
                .orElse(null);
    }

    // 店舗IDとスタッフIDを指定して該当スタッフの休憩時間を取得する
    public void updateBreakTime(ActiveStaffKey activeStaffKey, BreakStartTime breakStartTime, BreakEndTime breakEndTime) {
        activeStaffs.stream()
                .filter(activeStaff -> activeStaff.getKey().equals(activeStaffKey))
                .findFirst()
                .ifPresent(activeStaff -> activeStaff.setBreakTime(breakStartTime, breakEndTime));
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
