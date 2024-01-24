package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.valueobject.ReservationId;
import com.qms.mainservice.domain.repository.ReservationRepository;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchReservationDetailUsecase extends Usecase<ReservationId, FetchReservationDetailOutput> {

    private final ReservationRepository reservationRepository;

    public FetchReservationDetailOutput execute(ReservationId reservationId) {






        return null;
    }

}
