package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.repository.ReservationRepository;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateReservationStatusUsecase extends Usecase<UpdateReservationStatusInput, Void> {

    private final ReservationRepository reservationRepository;


    @Override
    public Void execute(UpdateReservationStatusInput input) {
        // 予約IDから予約を取得する
        Reservation reservation = reservationRepository.findById(input.reservationId());

        switch (input.status()) {
            case WAITING:
                // 予約のステータスを「未案内」に更新する
                reservation.updateStatusToWaiting();
                break;
            case IN_PROGRESS:
                // 予約のステータスを「対応中」に更新する
                reservation.updateStatusToInProgress(input.staffId());
                break;
            case DONE:
                // 予約のステータスを「案内済」に更新する
                reservation.updateStatusToDone();
                break;
            case PENDING:
                // 予約のステータスを「保留」に更新する
                reservation.updateStatusToPending();
                break;
            case CANCELED:
                // 予約のステータスを「キャンセル」に更新する
                reservation.updateStatusToCanceled();
                break;
            default:
                throw new IllegalArgumentException("Invalid value for Status: " + input.status());
        }

        return null;
    }

}
