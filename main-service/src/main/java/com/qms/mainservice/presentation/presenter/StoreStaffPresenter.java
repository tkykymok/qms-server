package com.qms.mainservice.presentation.presenter;

import com.qms.mainservice.application.usecase.storestaff.FetchStoreStaffsOutput;
import com.qms.mainservice.domain.model.valueobject.ImageUrl;
import com.qms.mainservice.domain.model.valueobject.SortOrder;
import com.qms.mainservice.presentation.web.response.storestaff.GetStoreStaffs;
import com.qms.mainservice.presentation.web.response.storestaff.StoreStaffResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
                                .imageUrl(Optional.ofNullable(storeStaffOutput.imageUrl())
                                        .map(ImageUrl::value)
                                        .orElse(null))
                                .isActive(storeStaffOutput.isActive().value())
                                .sortOrder(Optional.ofNullable(storeStaffOutput.sortOrder())
                                        .map(SortOrder::value)
                                        .orElse(null))
                                .breakStartTime(Optional.ofNullable(storeStaffOutput.breakStartTime())
                                        .map(breakStartTime -> breakStartTime.value().toString())
                                        .orElse(null))
                                .breakEndTime(Optional.ofNullable(storeStaffOutput.breakEndTime())
                                        .map(breakEndTime -> breakEndTime.value().toString())
                                        .orElse(null))
                                .build())
                        .toList())
                .build();

        return ResponseEntity.ok(response);
    }
}
