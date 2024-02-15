package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.valueobject.ReservedDate;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import lombok.Builder;

@Builder
public record FetchReservationsInput(
        StoreId storeId,
        ReservedDate reservedDate
) {
}
