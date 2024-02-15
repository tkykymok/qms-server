package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.repository.ReservationRepository;
import com.qms.mainservice.domain.service.SalesCreator;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateReservationStatusUsecase extends Usecase<UpdateReservationStatusInput, UpdateReservationStatusOutput> {

    private final ReservationRepository reservationRepository;
    private final SalesCreator salesCreator;

    @Transactional
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

        // 更新後のステータスが案内済の場合、売上を作成する
        if (reservation.getStatus().isDone()) {
            salesCreator.createSales(reservation);
        }

        return UpdateReservationStatusOutput.builder()
                .reservation(ReservationOutputMapper.modelToReservationOutput(reservation))
                .build();
    }

}
