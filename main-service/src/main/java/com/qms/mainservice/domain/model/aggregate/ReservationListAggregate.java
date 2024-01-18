package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.valueobject.ReservationId;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.model.valueobject.Time;

import java.util.List;

public class ReservationListAggregate {
    // 店舗ID
    private StoreId storeId;
    // 活動スタッフ一覧
    private List<ActiveStaff> activeStaffs;
    // 予約一覧
    private List<ReservationAggregate> reservations;

    private ReservationListAggregate() {
    }

    // 最後尾の待ち時間を算出する
    public Time calcLastWaitingTime() {
        return null;
    }

    // 該当予約の待ち時間を算出する(予約ID)
    public Time calcWaitingTime(ReservationId reservationId) {
        return null;
    }

    // DBから取得したデータをドメインオブジェクトに変換する
    public static ReservationListAggregate reconstruct(
            StoreId storeId,
            List<ActiveStaff> activeStaffs,
            List<ReservationAggregate> reservations
    ) {
        ReservationListAggregate reservationListAggregate = new ReservationListAggregate();
        reservationListAggregate.storeId = storeId;
        reservationListAggregate.activeStaffs = activeStaffs;
        reservationListAggregate.reservations = reservations;
        return reservationListAggregate;
    }


}
