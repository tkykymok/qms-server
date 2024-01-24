package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.model.valueobject.ReservationId;
import com.qms.mainservice.domain.model.valueobject.ReservedDate;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.repository.ReservationRepository;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FetchReservationOverview extends Usecase<ReservationId, FetchReservationOverviewOutput> {

    private final ReservationRepository reservationRepository;

    @Override
    public FetchReservationOverviewOutput execute(ReservationId input) throws IOException {


        return null;
    }
}
