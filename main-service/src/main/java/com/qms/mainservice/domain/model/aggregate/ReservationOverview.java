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
    private final PriorityQueue<StaffAvailability> staffAvailability = new PriorityQueue<>();

    // デフォルトコンストラクタ
    private ReservationOverview() {
    }


    // 順番を指定して待ち時間を算出する
    // 引数がnullの場合は最後尾の待ち時間を算出する
    public Time calcWaitTime(Position position) {
        // スタッフの次の利用可能時間を保持する優先度キューを初期化する
        initializeStaffAvailability();

        // スタッフの次の利用可能時間を保持する優先度キューが空の場合は0分を返す
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
            poll.addTime(waitingReservation.getTotalTime());
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

    // 案内開始時間目安を算出する(予約)
    public ServiceStartTime getEstimatedServiceStartTime(Reservation reservation) {
        Position position = getPosition(reservation);
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

    // 該当予約の順番を取得する(予約)
    public Position getPosition(Reservation reservation) {
        List<Reservation> waitingReservations = getWaitingReservations();

        // 予約がnullの場合、またはリスト内に予約が存在しない場合は最後尾の順番を返す
        if (reservation == null || waitingReservations.stream()
                .noneMatch(r -> Objects.equals(r.getId(), reservation.getId()))) {
            return Position.of(waitingReservations.size() + 1);
        }

        // Stream APIを使用して該当予約の位置を1ベースで取得
        return waitingReservations.stream()
                .filter(r -> Objects.equals(r.getId(), reservation.getId()))
                .findFirst()
                .map(r -> Position.of(waitingReservations.indexOf(r) + 1))
                .orElse(Position.of(waitingReservations.size() + 1)); // この行は実際には実行されないが、コンパイラの満足のために必要
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

    // スタッフの利用可能時間を計算し、優先度キューに格納する
    private void initializeStaffAvailability() {
        staffAvailability.clear(); // 優先度キューをクリア
        activeStaffs.forEach(staff -> {
            StaffId staffId = staff.getKey().staffId();
            List<Reservation> inProgressReservations = getReservationsByStaffId(staffId);
            Time nextAvailableTime = inProgressReservations.stream()
                    .map(Reservation::getTotalTime)
                    .reduce(Time.ZERO(), Time::add);

            staffAvailability.add(StaffAvailability.create(staff, nextAvailableTime));
        });
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
                .filter(reservation -> Objects.equals(reservation.getStaffId(), staffId))
                .filter(reservation -> Objects.equals(reservation.getStatus(), ReservationStatus.IN_PROGRESS))
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
        return overview;
    }


}
