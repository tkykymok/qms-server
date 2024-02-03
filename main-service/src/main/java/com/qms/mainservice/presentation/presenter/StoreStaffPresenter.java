package com.qms.mainservice.presentation.presenter;

import com.qms.mainservice.application.usecase.storestaff.FetchStoreStaffsOutput;
import com.qms.mainservice.presentation.web.response.storestaff.GetStoreStaffs;
import com.qms.mainservice.presentation.web.response.storestaff.StoreStaffResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class StoreStaffPresenter {
    public ResponseEntity<GetStoreStaffs> present(FetchStoreStaffsOutput output) {
        var response = GetStoreStaffs.builder()
                .storeStaffs(output.storeStaffOutputs().stream()
                        .map(storeStaffOutput -> StoreStaffResponse.builder()
                                .staffId(storeStaffOutput.staffId().value())
                                .storeId(storeStaffOutput.storeId().value())
                                .lastName(storeStaffOutput.lastName().value())
                                .firstName(storeStaffOutput.firstName().value())
                                .isActive(storeStaffOutput.isActive().value())
                                .sortOrder(storeStaffOutput.sortOrder().value())
                                .reservationId(storeStaffOutput.reservationId().value())
                                .build())
                        .toList())
                .build();

        return ResponseEntity.ok(response);
    }
}
