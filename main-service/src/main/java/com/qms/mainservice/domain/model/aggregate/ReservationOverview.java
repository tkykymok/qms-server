package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.entity.StaffAvailability;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.exception.DomainException;
import com.qms.shared.domain.model.AggregateRoot;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ReservationOverview extends AggregateRoot<StoreId> {
    // 店舗情報
    private StoreName storeName;
    // 活動スタッフ一覧
    private List<ActiveStaff> activeStaffs;
    // 予約一覧
    private List<Reservation> reservations;
    // スタッフの次の利用可能時刻を保持する優先度キュー
    private PriorityQueue<StaffAvailability> staffAvailability;

    // デフォルトコンストラクタ
    private ReservationOverview() {
    }



    // 順番を指定して待ち時間を算出する
    public Time calcWaitTime(Position position) {
        if (staffAvailability.isEmpty()) {
            return Time.ZERO();
        }

        // 予約一覧の予約ステータスが未案内の予約一覧を取得する
        List<Reservation> waitingReservations = getWaitingReservations();

        int targetPosition;
        if (position == null) {
            // 順番が指定されていない場合は最後尾の予約を対象とする
            targetPosition = waitingReservations.size();
        } else {
            // 順番が指定されている場合は指定された順番の予約を対象とする
            targetPosition = position.value() - 1;
        }

        for (int i = 0; i < waitingReservations.size(); i++) {
            Reservation waitingReservation = waitingReservations.get(i);
            // スタッフの次の利用可能時刻を保持する優先度キューから最小値を取得する
            StaffAvailability poll = staffAvailability.poll();
            // スタッフの次の利用可能時刻に予約の所要時間を加算する
            if (poll == null) {
                throw new DomainException("スタッフの次の利用可能時刻を保持する優先度キューが空です");
            }
            // スタッフの次の利用可能時刻を保持する優先度キューに追加する
            poll.addTime(waitingReservation.getTime());
            // ループ対象の予約が処理される時刻がスタッフの休憩時間内の場合、次の利用可能時刻に休憩終了時刻を追加する
            poll.addBreakTimeIfNeeded();
            // スタッフの次の利用可能時刻を保持する優先度キューに追加する
            staffAvailability.add(poll);
            if (i == targetPosition) {
                // ループ対象の予約が処理される時刻を取得する(現在時刻から何分後か)
                return poll.getNextAvailableTime();
            }
        }

        // 最後尾の予約が処理される時刻を取得する(現在時刻から何分後か)
        return staffAvailability.stream()
                .min(StaffAvailability::compareTo)
                .orElseThrow(() -> new DomainException("スタッフの次の利用可能時刻を保持する優先度キューが空です"))
                .getNextAvailableTime();
    }

    // 最後尾の待ち時間を算出する
    public Time calcLastWaitTime() {
        return calcWaitTime(null);
    }

    // 該当予約の待ち時間を算出し、案内開始時刻目安を算出する(予約ID)
    public ServiceStartDateTime calcEstimatedServiceStartDateTime(Position position) {
        Time time = calcWaitTime(position);
        return ServiceStartDateTime.of(LocalDateTime.now().plusMinutes(time.value()));
    }

    // 該当予約の順番を取得する(予約ID)
    public Position getPosition(ReservationId reservationId) {
        // 予約一覧の予約ステータスが未案内の予約一覧を取得する
        List<Reservation> waitingReservations = getWaitingReservations();
        // 該当予約の順番を取得する
        for (int i = 0; i < waitingReservations.size(); i++) {
            Reservation reservation = waitingReservations.get(i);
            if (reservation.getId().equals(reservationId)) {
                return Position.of(i + 1);
            }
        }
        // 該当予約が見つからない場合はnullを返却する
        return null;
    }


    // 予約ステータスが未案内の予約一覧を取得する
    private List<Reservation> getWaitingReservations() {
        return reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getStatus(), ReservationStatus.WAITING))
                .sorted((r1, r2) -> {
                    ReservationNumber num1 = r1.getReservationNumber();
                    ReservationNumber num2 = r2.getReservationNumber();
                    return num1.compareTo(num2);
                })
                .toList();
    }


    // 予約日を取得する
    public ReservedDate getReservedDate() {
        return reservations.stream()
                .findFirst()
                .map(Reservation::getReservedDate)
                .orElse(null);
    }

    // 予約一覧から予約IDに紐づく予約を取得する
    public Reservation getReservationById(ReservationId reservationId) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(reservationId))
                .findFirst()
                .orElse(null);
    }


    // DBから取得したデータをドメインオブジェクトに変換する
    public static ReservationOverview reconstruct(
            Store store,
            List<ActiveStaff> activeStaffs,
            List<Reservation> reservations) {
        ReservationOverview overview = new ReservationOverview();
        overview.id = store.getId();
        overview.storeName = store.getStoreName();
        overview.activeStaffs = activeStaffs;
        overview.reservations = reservations;

        // スタッフの次の利用可能時刻を保持する優先度キューを初期化する
        overview.staffAvailability = new PriorityQueue<>();

        activeStaffs.forEach(staff -> {
            Reservation reservation = null;
            ReservationId reservationId = staff.getReservationId();
            if (reservationId != null) {
                reservation = overview.getReservationById(reservationId);
            }
            Time nextAvailableTime = (reservation != null) ? reservation.getTime() : Time.ZERO();
            overview.staffAvailability.add(StaffAvailability.create(staff, nextAvailableTime));
        });

        return overview;
    }


}
