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

        // 予約のステータスを更新する
        reservation.updateStatus(input.status(), input.staffId());

        // 予約を更新する
        reservationRepository.update(reservation);

        return UpdateReservationStatusOutput.builder()
                .reservation(ReservationOutputMapper.modelToReservationOutput(reservation))
                .build();
    }

}
