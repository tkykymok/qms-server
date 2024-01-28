package com.qms.mainservice.application.usecase.staff;

import com.qms.mainservice.domain.model.aggregate.StoreStaffOverview;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.service.StoreStaffOverviewCreator;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchStoreStaffsUsecase extends Usecase<StoreId, FetchStoreStaffsOutput> {

    private final StoreStaffOverviewCreator storeStaffOverviewCreator;

    @Override
    public FetchStoreStaffsOutput execute(StoreId input) {
        // 店舗IDを指定して店舗スタッフ状況集約を生成する
        StoreStaffOverview storeStaffOverview = storeStaffOverviewCreator.create(input);

        // 店舗スタッフ状況集約を出力用のDTOに変換する
        return FetchStoreStaffsOutput.builder()
                .storeStaffOutputs(
                        storeStaffOverview.getStoreStaffs().stream()
                                .map(storeStaff -> StoreStaffOutput.builder()
                                        .staffId(storeStaff.getKey().staffId())
                                        .storeId(storeStaff.getKey().storeId())
                                        .companyId(storeStaff.getStaff().getCompanyId())
                                        .lastName(storeStaff.getStaff().getLastName())
                                        .firstName(storeStaff.getStaff().getFirstName())
                                        .cognitoUserId(storeStaff.getStaff().getCognitoUserId())
                                        .isActive(storeStaffOverview.getIsActive(
                                                storeStaff.getKey().storeId(),
                                                storeStaff.getKey().staffId()
                                        ))
                                        .build()
                                )
                                .toList()
                )
                .activeStaffOutputs(
                        storeStaffOverview.getActiveStaffs().stream()
                                .map(activeStaff -> ActiveStaffOutput.builder()
                                        .storeId(activeStaff.getKey().storeId())
                                        .staffId(activeStaff.getKey().staffId())
                                        .sortOrder(activeStaff.getSortOrder())
                                        .breakStartTime(activeStaff.getBreakStartTime())
                                        .breakEndTime(activeStaff.getBreakEndTime())
                                        .reservationId(activeStaff.getReservationId())
                                        .companyId(activeStaff.getStaff().getCompanyId())
                                        .lastName(activeStaff.getStaff().getLastName())
                                        .firstName(activeStaff.getStaff().getFirstName())
                                        .cognitoUserId(activeStaff.getStaff().getCognitoUserId())
                                        .build()
                                ).toList()
                )
                .build();
    }
}
