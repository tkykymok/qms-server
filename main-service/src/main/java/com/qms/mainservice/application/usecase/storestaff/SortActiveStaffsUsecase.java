package com.qms.mainservice.application.usecase.storestaff;

import com.qms.mainservice.domain.model.aggregate.StoreStaffOverview;
import com.qms.mainservice.domain.repository.StoreStaffRepository;
import com.qms.mainservice.domain.service.StoreStaffOverviewCreator;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SortActiveStaffsUsecase extends Usecase<SortActiveStaffsInput, Void> {

    private final StoreStaffOverviewCreator storeStaffOverviewCreator;
    private final StoreStaffRepository storeStaffRepository;

    @Transactional
    @Override
    public Void execute(SortActiveStaffsInput input) {
        // 店舗IDを指定して店舗スタッフ状況集約を生成する
        StoreStaffOverview storeStaffOverview = storeStaffOverviewCreator.create(input.storeId());

        // 活動中スタッフの並び順を更新する
        storeStaffOverview.sortActiveStaffs(input.staffIds());
        storeStaffRepository.updateActiveStaffSortOrder(storeStaffOverview);

        return null;
    }
}
