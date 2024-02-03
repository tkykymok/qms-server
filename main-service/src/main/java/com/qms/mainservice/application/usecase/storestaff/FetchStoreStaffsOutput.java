package com.qms.mainservice.application.usecase.storestaff;

import lombok.Builder;

import java.util.List;

@Builder
public record FetchStoreStaffsOutput(
        List<StoreStaffOutput> storeStaffOutputs
) {
}
