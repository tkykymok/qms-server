package com.qms.mainservice.presentation.presenter;

import com.qms.mainservice.application.usecase.staff.FetchStoreStaffsOutput;
import com.qms.mainservice.presentation.web.response.staff.ActiveStaffResponse;
import com.qms.mainservice.presentation.web.response.staff.GetStoreStaffs;
import com.qms.mainservice.presentation.web.response.staff.StoreStaffResponse;
import com.qms.shared.utils.Formatter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class StaffPresenter {
    public ResponseEntity<GetStoreStaffs> present(FetchStoreStaffsOutput output) {
        var response = GetStoreStaffs.builder()
                .storeStaffs(output.storeStaffOutputs().stream()
                        .map(storeStaffOutput -> StoreStaffResponse.builder()
                                .staffId(storeStaffOutput.staffId().value())
                                .storeId(storeStaffOutput.storeId().value())
                                .lastName(storeStaffOutput.lastName().value())
                                .firstName(storeStaffOutput.firstName().value())
                                .isActive(storeStaffOutput.isActive().value())
                                .build())
                        .toList())
                .activeStaffs(output.activeStaffOutputs().stream()
                        .map(activeStaffOutput -> ActiveStaffResponse.builder()
                                .storeId(activeStaffOutput.storeId().value())
                                .staffId(activeStaffOutput.staffId().value())
                                .sortOrder(activeStaffOutput.sortOrder().value())
                                .lastName(activeStaffOutput.lastName().value())
                                .firstName(activeStaffOutput.firstName().value())
                                .breakStartTime(
                                        Formatter.formatTime(activeStaffOutput.breakStartTime().value(), "HH:mm")
                                )
                                .breakEndTime(
                                        Formatter.formatTime(activeStaffOutput.breakEndTime().value(), "HH:mm")
                                )
                                .reservationId(activeStaffOutput.reservationId().value())
                                .build())
                        .toList())
                .build();

        return ResponseEntity.ok(response);
    }
}
