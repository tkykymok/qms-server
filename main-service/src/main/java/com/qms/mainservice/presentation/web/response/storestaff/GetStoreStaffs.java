package com.qms.mainservice.presentation.web.response.storestaff;

import lombok.Builder;

import java.util.List;

@Builder
public record GetStoreStaffs(
        List<StoreStaffResponse> storeStaffs
) {
}
