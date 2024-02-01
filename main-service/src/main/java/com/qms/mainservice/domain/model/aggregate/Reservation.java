package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.entity.Customer;
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
    private StaffId staffId; // 対応スタッフID *更新対象
    private ServiceStartTime serviceStartTime; // 対応開始時間 *更新対象
    private ServiceEndTime serviceEndTime; // 対応終了時間 *更新対象
    private HoldStartTime holdStartTime; // 保留開始時間 *更新対象
    private ReservationStatus status; // 予約ステータス *更新対象
    private Flag notified; // 通知フラグ *更新対象
    private Flag arrived; // 到着フラグ *更新対象
    private VersionKey version; // バージョン
    // 店舗情報
    private Store store;
    // 予約メニューList
    private List<ReservationMenu> reservationMenus;
    // 顧客情報
    private Customer customer;

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
     * 予約ステータスを更新する
     *
     * @param afterStatus 更新後の予約ステータス
     * @param staffId     対応スタッフID
     */
    public void updateStatus(ReservationStatus afterStatus, StaffId staffId) {
        switch (afterStatus) {
            // 予約のステータスを「未案内」に更新する
            case WAITING -> updateStatusToWaiting();
            // 予約のステータスを「対応中」に更新する
            case IN_PROGRESS -> updateStatusToInProgress(staffId);
            // 予約のステータスを「案内済」に更新する
            case DONE -> updateStatusToDone();
            // 予約のステータスを「保留」に更新する
            case PENDING -> updateStatusToPending();
            // 予約のステータスを「キャンセル」に更新する
            case CANCELED -> updateStatusToCanceled();
            default -> throw new IllegalArgumentException("Invalid value for Status: " + status);
        }

    }

    /**
     * 予約ステータスを未案内に更新する
     */
    private void updateStatusToWaiting() {
        // 現在のステータスが案内済の場合は例外をスローする
        if (this.status == ReservationStatus.DONE) {
            throw new DomainException("案内済の予約は未案内にできません。");
        }
        this.status = ReservationStatus.WAITING;
        this.serviceStartTime = ServiceStartTime.of(null);
        this.holdStartTime = HoldStartTime.of(null);
        this.version = version.increment();
    }

    /**
     * 予約ステータスを対応中に更新する
     *
     * @param staffId 対応スタッフID
     */
    private void updateStatusToInProgress(StaffId staffId) {
        // 現在のステータスがキャンセル|案内済の場合は例外をスローする
        if (this.status == ReservationStatus.CANCELED || this.status == ReservationStatus.DONE) {
            throw new DomainException("キャンセルまたは案内済の予約は対応中にできません。");
        }
        this.status = ReservationStatus.IN_PROGRESS; // 予約ステータスを対応中に更新する
        this.staffId = staffId; // 対応スタッフを設定する
        this.serviceStartTime = ServiceStartTime.now(); // 対応開始時間を設定する
        this.version = version.increment();
    }

    /**
     * 予約ステータスを保留に更新する
     */
    private void updateStatusToPending() {
        // 現在のステータスが案内済の場合は例外をスローする
        if (this.status == ReservationStatus.DONE) {
            throw new DomainException("案内済の予約は保留にできません。");
        }

        this.status = ReservationStatus.PENDING; // 予約ステータスを保留中に更新する
        this.holdStartTime = HoldStartTime.now(); // 保留開始時間を設定する
        this.version = version.increment();
    }

    /**
     * 予約ステータスを案内済に更新する
     */
    private void updateStatusToDone() {
        // 現在のステータスが対応中でない場合は例外をスローする
        if (this.status != ReservationStatus.IN_PROGRESS) {
            throw new DomainException("対応中の予約のみ案内済にできます。");
        }
        this.status = ReservationStatus.DONE; // 予約ステータスを案内済に更新する
        this.serviceEndTime = ServiceEndTime.now(); // 対応終了時間を設定する
        this.version = version.increment();
    }

    /**
     * 予約ステータスをキャンセルに更新する
     */
    private void updateStatusToCanceled() {
        // 現在のステータスが保留でない場合は例外をスローする
        if (this.status != ReservationStatus.PENDING) {
            throw new DomainException("保留中の予約のみキャンセルにできます。");
        }
        this.status = ReservationStatus.CANCELED;
        this.version = version.increment();
    }

    /**
     * メニュー名を取得する
     * @return メニュー名
     */
    public MenuName getMenuName() {
        String menuNamesConcatenated = this.reservationMenus.stream()
                .map(ReservationMenu::getMenu)
                .map(Menu::getMenuName)
                .map(MenuName::value)
                .collect(Collectors.joining("、"));
        return MenuName.of(menuNamesConcatenated);
    }

    /**
     * メニュー金額を取得する
     * @return メニュー金額
     */
    public Price getPrice() {
        return this.reservationMenus.stream()
                .map(reservationMenu -> reservationMenu.getMenu().getPrice())
                .reduce(Price.ZERO(), Price::add);
    }

    /**
     * 予約の所要時間を取得する
     * @return 予約の所要時間
     */
    public Time getTime() {
        // 予約メニューから得られる時間を合計
        Time menuTime = Time.ZERO();
        for (ReservationMenu reservationMenu : reservationMenus) {
            menuTime = menuTime.add(reservationMenu.getMenu().getTime());
        }

        // 予約ステータスが対応中の場合、現在日時との差を計算して残り時間追加
        if (this.status == ReservationStatus.IN_PROGRESS) {
            Duration duration = Duration.between(serviceStartTime.value(), LocalTime.now()); // 経過時間
            Time remainingTime = menuTime.subtract(Time.of((int) duration.toMinutes())); // 所要時間 - 経過時間 = 残り時間
            return menuTime.add(remainingTime);
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
            List<ReservationMenu> reservationMenus,
            Customer customer
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
        reservation.customer = customer;
        return reservation;
    }

}
