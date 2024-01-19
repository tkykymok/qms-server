package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.entity.Menu;
import com.qms.mainservice.domain.model.entity.ReservationMenu;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.exception.DomainException;
import com.qms.shared.domain.model.AggregateRoot;
import lombok.Getter;

import java.util.List;

@Getter
public class ReservationAggregate extends AggregateRoot<ReservationId> {

    // 予約
    private CustomerId customerId; // 顧客ID
    private StoreId storeId; // 店舗ID
    private ReservationNumber reservationNumber; // 予約番号
    private ReservedDate reservedDate; // 予約日
    private StaffId staffId; // 対応スタッフID
    private ServiceStartDateTime serviceStartDateTime; // 対応開始日時
    private ServiceEndDateTime serviceEndDateTime; // 対応終了日時
    private HoldStartDateTime holdStartDateTime; // 保留開始日時
    private ReservationStatus status; // 予約ステータス
    private Flag notified; // 通知フラグ
    private Flag arrived; // 到着フラグ
    private VersionKey version; // バージョン
    // 予約メニューList
    List<ReservationMenu> reservationMenus;

    private ReservationAggregate() {
    }

    /**
     * 予約を作成する
     *
     * @param storeId           店舗ID
     * @param customerId        顧客ID
     * @param menus             メニュー一覧
     * @param reservationNumber 予約番号
     * @return 予約
     */
    public static ReservationAggregate create(
            StoreId storeId,
            CustomerId customerId,
            List<Menu> menus,
            ReservationNumber reservationNumber
    ) {
        // 予約を作成する
        ReservationAggregate reservationAggregate = new ReservationAggregate();
        reservationAggregate.customerId = customerId;
        reservationAggregate.storeId = storeId;
        reservationAggregate.reservationNumber = reservationNumber;
        reservationAggregate.reservedDate = ReservedDate.now();
        reservationAggregate.status = ReservationStatus.WAITING;
        reservationAggregate.notified = Flag.OFF();
        reservationAggregate.arrived = Flag.OFF();
        reservationAggregate.version = VersionKey.newVersion();

        // 予約メニューを作成する
        reservationAggregate.reservationMenus = menus.stream()
                .map(ReservationMenu::create)
                .toList();

        return reservationAggregate;
    }

    /**
     * 予約ステータスを未案内に更新する
     */
    public void updateStatusToWaiting() {
        this.status = ReservationStatus.WAITING;
        this.serviceStartDateTime = null;
        this.holdStartDateTime = null;
    }

    /**
     * 予約ステータスを対応中に更新する
     *
     * @param staffId 対応スタッフID
     */
    public void updateStatusToInProgress(StaffId staffId) {
        this.status = ReservationStatus.IN_PROGRESS; // 予約ステータスを対応中に更新する
        this.staffId = staffId; // 対応スタッフを設定する
        this.serviceStartDateTime = ServiceStartDateTime.now(); // 対応開始日時を設定する
    }

    /**
     * 予約ステータスを案内済に更新する
     */
    public void updateStatusToPending() {
        this.status = ReservationStatus.PENDING; // 予約ステータスを保留中に更新する
        this.holdStartDateTime = HoldStartDateTime.now(); // 保留開始日時を設定する
    }

    /**
     * 予約ステータスを案内済に更新する
     */
    public void updateStatusToDone() {
        // 現在のステータスが対応中でない場合は例外をスローする
        if (this.status != ReservationStatus.IN_PROGRESS) {
            throw new DomainException("現在のステータスが対応中ではありません。");
        }
        this.status = ReservationStatus.DONE; // 予約ステータスを案内済に更新する
        this.serviceEndDateTime = ServiceEndDateTime.now(); // 対応終了日時を設定する
    }

    /**
     * 予約ステータスをキャンセルに更新する
     */
    public void updateStatusToCanceled() {
        this.status = ReservationStatus.CANCELED;
    }

    // DBから取得したデータをドメインオブジェクトに変換する
    public static ReservationAggregate reconstruct(
            ReservationId reservationId,
            CustomerId customerId,
            StoreId storeId,
            ReservationNumber reservationNumber,
            ReservedDate reservedDate,
            StaffId staffId,
            ServiceStartDateTime serviceStartDateTime,
            ServiceEndDateTime serviceEndDateTime,
            HoldStartDateTime holdStartDateTime,
            ReservationStatus status,
            Flag notified,
            Flag arrived,
            VersionKey version,
            List<ReservationMenu> reservationMenus
    ) {
        ReservationAggregate reservationAggregate = new ReservationAggregate();
        reservationAggregate.id = reservationId;
        reservationAggregate.customerId = customerId;
        reservationAggregate.storeId = storeId;
        reservationAggregate.reservationNumber = reservationNumber;
        reservationAggregate.reservedDate = reservedDate;
        reservationAggregate.staffId = staffId;
        reservationAggregate.serviceStartDateTime = serviceStartDateTime;
        reservationAggregate.serviceEndDateTime = serviceEndDateTime;
        reservationAggregate.holdStartDateTime = holdStartDateTime;
        reservationAggregate.status = status;
        reservationAggregate.notified = notified;
        reservationAggregate.arrived = arrived;
        reservationAggregate.version = version;
        reservationAggregate.reservationMenus = reservationMenus;
        return reservationAggregate;
    }

}
