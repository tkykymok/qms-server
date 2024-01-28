package com.qms.mainservice.presentation.web.response.staff;

import lombok.Builder;

import java.util.List;

@Builder
public record GetStoreStaffs(
        List<StoreStaffResponse> storeStaffs,
        List<ActiveStaffResponse> activeStaffs
) {
}
