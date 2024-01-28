package com.qms.mainservice.domain.service;

import com.qms.mainservice.domain.model.aggregate.StoreStaffOverview;
import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.entity.StoreStaff;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.repository.ActiveStaffRepository;
import com.qms.mainservice.domain.repository.StoreStaffRepository;
import com.qms.shared.domain.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreStaffOverviewCreator implements DomainService {

    private final StoreStaffRepository storeStaffRepository;
    private final ActiveStaffRepository activeStaffRepository;

    // 店舗IDを指定して店舗スタッフ状況集約を生成する
    public StoreStaffOverview create(StoreId storeId) {
        // 店舗に紐づく店舗スタッフ一覧を取得する
        List<StoreStaff> storeStaffs = storeStaffRepository.findAllByStoreId(storeId);

        // 店舗に紐づく活動スタッフを取得する
        List<ActiveStaff> activeStaffs = activeStaffRepository.findAllByStoreId(storeId);

        // 予約状況集約を生成する
        return StoreStaffOverview
                .reconstruct(storeId, storeStaffs, activeStaffs);
    }
}
