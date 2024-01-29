package com.qms.mainservice.application.usecase.staff;

import lombok.Builder;

import java.util.List;

@Builder
public record FetchStoreStaffsOutput(
        List<StoreStaffOutput> storeStaffOutputs
) {
}
