package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.entity.Menu;
import com.qms.mainservice.domain.model.entity.ReservationMenu;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.exception.DomainException;
import com.qms.shared.domain.model.AggregateRoot;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Reservation extends AggregateRoot<ReservationId> {

    // 予約
    private CustomerId customerId; // 顧客ID
    private StoreId storeId; // 店舗ID
    private ReservationNumber reservationNumber; // 予約番号
    private ReservedDate reservedDate; // 予約日
    private StaffId staffId; // 対応スタッフID
    private ServiceStartTime serviceStartTime; // 対応開始時間
    private ServiceEndTime serviceEndTime; // 対応終了時間
    private HoldStartTime holdStartTime; // 保留開始時間
    private ReservationStatus status; // 予約ステータス
    private Flag notified; // 通知フラグ
    private Flag arrived; // 到着フラグ
    private VersionKey version; // バージョン
    // 店舗情報
    private Store store;
    // 予約メニューList
    private List<ReservationMenu> reservationMenus;

    private Reservation() {
    }

    /**
     * 予約を作成する
     *
     * @param storeId           店舗ID
     * @param customerId        顧客ID
     * @param storeMenuIds      店舗メニューID一覧
     * @param reservationNumber 予約番号
     * @return 予約
     */
    public static Reservation create(
            StoreId storeId,
            CustomerId customerId,
            List<StoreMenuId> storeMenuIds,
            ReservationNumber reservationNumber
    ) {
        // 予約を作成する
        Reservation reservation = new Reservation();
        reservation.customerId = customerId;
        reservation.storeId = storeId;
        reservation.reservationNumber = reservationNumber;
        reservation.reservedDate = ReservedDate.now();
        reservation.status = ReservationStatus.WAITING;
        reservation.notified = Flag.OFF();
        reservation.arrived = Flag.OFF();
        reservation.version = VersionKey.newVersion();

        // 予約メニューを作成する
        reservation.reservationMenus = storeMenuIds.stream()
                .map(storeMenuId -> ReservationMenu.create(reservation.id, storeId, storeMenuId))
                .toList();

        return reservation;
    }

    /**
     * 予約ステータスを未案内に更新する
     */
    public void updateStatusToWaiting() {
        this.status = ReservationStatus.WAITING;
        this.serviceStartTime = null;
        this.holdStartTime = null;
    }

    /**
     * 予約ステータスを対応中に更新する
     *
     * @param staffId 対応スタッフID
     */
    public void updateStatusToInProgress(StaffId staffId) {
        this.status = ReservationStatus.IN_PROGRESS; // 予約ステータスを対応中に更新する
        this.staffId = staffId; // 対応スタッフを設定する
        this.serviceStartTime = ServiceStartTime.now(); // 対応開始時間を設定する
    }

    /**
     * 予約ステータスを案内済に更新する
     */
    public void updateStatusToPending() {
        this.status = ReservationStatus.PENDING; // 予約ステータスを保留中に更新する
        this.holdStartTime = HoldStartTime.now(); // 保留開始時間を設定する
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
        this.serviceEndTime = ServiceEndTime.now(); // 対応終了時間を設定する
    }

    /**
     * 予約ステータスをキャンセルに更新する
     */
    public void updateStatusToCanceled() {
        this.status = ReservationStatus.CANCELED;
    }

    // メニュー名を取得する
    public MenuName getMenuName() {
        String menuNamesConcatenated = this.reservationMenus.stream()
                .map(ReservationMenu::getMenu)
                .map(Menu::getMenuName)
                .map(MenuName::value)
                .collect(Collectors.joining("、"));
        return MenuName.of(menuNamesConcatenated);
    }

    // メニュー金額を取得する
    public Price getPrice() {
        return this.reservationMenus.stream()
                .map(reservationMenu -> reservationMenu.getMenu().getPrice())
                .reduce(Price.ZERO(), Price::add);
    }

    // 予約の所要時間を取得する
    public Time getTime() {
        // 予約メニューから得られる時間を合計
        Time menuTime = this.reservationMenus.stream()
                .map(reservationMenu -> reservationMenu.getMenu().getTime())
                .reduce(Time.ZERO(), Time::add);

        // 予約ステータスが対応中の場合、現在日時との差を計算して追加
        if (this.status == ReservationStatus.IN_PROGRESS) {
            Duration duration = Duration.between(serviceStartTime.value(), LocalTime.now());
            Time additionalTime = Time.of((int) duration.toMinutes());
            return menuTime.add(additionalTime);
        }

        return menuTime;
    }

    // DBから取得したデータをドメインオブジェクトに変換する
    public static Reservation reconstruct(
            ReservationId reservationId,
            CustomerId customerId,
            StoreId storeId,
            ReservationNumber reservationNumber,
            ReservedDate reservedDate,
            StaffId staffId,
            ServiceStartTime serviceStartTime,
            ServiceEndTime serviceEndTime,
            HoldStartTime holdStartTime,
            ReservationStatus status,
            Flag notified,
            Flag arrived,
            VersionKey version,
            Store store,
            List<ReservationMenu> reservationMenus
    ) {
        Reservation reservation = new Reservation();
        reservation.id = reservationId;
        reservation.customerId = customerId;
        reservation.storeId = storeId;
        reservation.reservationNumber = reservationNumber;
        reservation.reservedDate = reservedDate;
        reservation.staffId = staffId;
        reservation.serviceStartTime = serviceStartTime;
        reservation.serviceEndTime = serviceEndTime;
        reservation.holdStartTime = holdStartTime;
        reservation.status = status;
        reservation.notified = notified;
        reservation.arrived = arrived;
        reservation.version = version;
        reservation.store = store;
        reservation.reservationMenus = reservationMenus;
        return reservation;
    }

}