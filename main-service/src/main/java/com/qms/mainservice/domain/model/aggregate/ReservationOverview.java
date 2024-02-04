package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.entity.StaffAvailability;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.exception.DomainException;
import com.qms.shared.domain.model.AggregateRoot;

import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class ReservationOverview extends AggregateRoot<StoreId> {
    // 活動スタッフ一覧
    private List<ActiveStaff> activeStaffs;
    // 予約一覧
    private List<Reservation> reservations;
    // スタッフの次の利用可能時間を保持する優先度キュー
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
            // スタッフの次の利用可能時間を保持する優先度キューから最小値を取得する
            StaffAvailability poll = staffAvailability.poll();
            // スタッフの次の利用可能時間に予約の所要時間を加算する
            if (poll == null) {
                throw new DomainException("スタッフの次の利用可能時間を保持する優先度キューが空です");
            }

            // ループ対象が対象の予約の場合は処理を終了する
            if (i == targetPosition) {
                // ループ対象の予約が処理される時間を取得する(現在時間から何分後か)
                return poll.getNextAvailableTime();
            }

            // スタッフの次の利用可能時間を保持する優先度キューに追加する
            poll.addTime(waitingReservation.getTime());
            // ループ対象の予約が処理される時間がスタッフの休憩時間内の場合、次の利用可能時間に休憩終了時間を追加する
            poll.addBreakTimeIfNeeded();
            // スタッフの次の利用可能時間を保持する優先度キューに追加する
            staffAvailability.add(poll);
        }

        // 最後尾の予約が処理される時間を取得する(現在時間から何分後か)
        return staffAvailability.stream()
                .min(StaffAvailability::compareTo)
                .orElseThrow(() -> new DomainException("スタッフの次の利用可能時間を保持する優先度キューが空です"))
                .getNextAvailableTime();
    }

    // 最後尾の待ち時間を算出する
    public Time calcLastWaitTime() {
        return calcWaitTime(null);
    }

    // 案内開始時間目安を算出する(予約ID)
    public ServiceStartTime getEstimatedServiceStartTime(Position position) {
        Time time = calcWaitTime(position);
        return ServiceStartTime.nowPlusTime(time);
    }

    // 未案内の予約一覧の件数を取得する
    public Count getWaitingCount() {
        return Count.of(getWaitingReservations().size());
    }

    // 活動中スタッフの数を取得する
    public Count getActiveStaffCount() {
        return Count.of(activeStaffs.size());
    }

    // 該当予約の順番を取得する(予約ID)
    public Position getPosition(ReservationId reservationId) {
        // 予約ステータスが未案内の予約一覧を取得する
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
                .orElse(ReservedDate.of(null));
    }

    // 予約一覧から予約IDに紐づく予約を取得する
    public Reservation getReservationById(ReservationId reservationId) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(reservationId))
                .findFirst()
                .orElse(null);
    }

    // 予約一覧からスタッフIDに紐づく対応中の予約(複数有)を取得する
    public List<Reservation> getReservationsByStaffId(StaffId staffId) {
        return reservations.stream()
                .filter(reservation -> reservation.getStaffId().equals(staffId))
                .filter(reservation -> reservation.getStatus().equals(ReservationStatus.IN_PROGRESS))
                .toList();
    }



    // DBから取得したデータをドメインオブジェクトに変換する
    public static ReservationOverview reconstruct(
            StoreId storeId,
            List<ActiveStaff> activeStaffs,
            List<Reservation> reservations) {
        ReservationOverview overview = new ReservationOverview();
        overview.id = storeId;
        overview.activeStaffs = activeStaffs;
        overview.reservations = reservations;

        // スタッフの次の利用可能時間を保持する優先度キューを初期化する
        overview.staffAvailability = new PriorityQueue<>();

        // 活動中スタッフ一覧から次の利用可能時間を算出する
        overview.activeStaffs.forEach(staff -> {
            StaffId staffId = staff.getKey().staffId();
            List<Reservation> inProgressReservations = overview.getReservationsByStaffId(staffId);
            if (!inProgressReservations.isEmpty()) {
                // 所要時間の合計を算出する
                Time nextAvailableTime = inProgressReservations.stream()
                        .map(Reservation::getTime)
                        .reduce(Time.ZERO(), Time::add);

                overview.staffAvailability.add(StaffAvailability.create(staff, nextAvailableTime));
            }
        });

        return overview;
    }


}
