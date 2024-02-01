package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.repository.ReservationRepository;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateReservationStatusUsecase extends Usecase<UpdateReservationStatusInput, UpdateReservationStatusOutput> {

    private final ReservationRepository reservationRepository;

    @Override
    public UpdateReservationStatusOutput execute(UpdateReservationStatusInput input) {
        // 予約IDから予約を取得する
        Reservation reservation = reservationRepository.findById(input.reservationId());
        // バージョンをチェックする
        if (!reservation.getVersion().equals(input.version())) {
            throw new IllegalArgumentException("Invalid version: " + input.version());
        }

        switch (input.status()) {
            // 予約のステータスを「未案内」に更新する
            case WAITING -> reservation.updateStatusToWaiting();
            // 予約のステータスを「対応中」に更新する
            case IN_PROGRESS -> reservation.updateStatusToInProgress(input.staffId());
            // 予約のステータスを「案内済」に更新する
            case DONE -> reservation.updateStatusToDone();
            // 予約のステータスを「保留」に更新する
            case PENDING -> reservation.updateStatusToPending();
            // 予約のステータスを「キャンセル」に更新する
            case CANCELED -> reservation.updateStatusToCanceled();
            default -> throw new IllegalArgumentException("Invalid value for Status: " + input.status());
        }

        // 予約を更新する
        reservationRepository.update(reservation);

        return UpdateReservationStatusOutput.builder()
                .reservation(ReservationOutputMapper.modelToReservationOutput(reservation))
                .build();
    }

}
