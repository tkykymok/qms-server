package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.repository.ReservationRepository;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FetchReservationsUsecase extends Usecase<FetchReservationsInput, FetchReservationsOutput> {

    private final ReservationRepository reservationRepository;

    public FetchReservationsOutput execute(FetchReservationsInput input) {
        // 店舗IDから当日の予約一覧を取得する
        List<Reservation> result =
                reservationRepository.findAllByStoreIdAndReservedDate(input.storeId(), input.reservedDate());
        if (result.isEmpty()) {
            // 予約一覧が存在しない場合は空の予約一覧を返す
            return FetchReservationsOutput.builder()
                    .reservations(List.of())
                    .build();
        }

        List<ReservationOutput> reservationOutputs = result.stream()
                .map(ReservationOutputMapper::modelToReservationOutput)
                .toList();

        // 予約一覧を返す
        return FetchReservationsOutput.builder()
                .reservations(reservationOutputs)
                .build();
    }

}
