package com.qms.mainservice.application.usecase.storestaff;

import com.qms.mainservice.domain.model.aggregate.StoreStaffOverview;
import com.qms.mainservice.domain.repository.StoreStaffRepository;
import com.qms.mainservice.domain.service.ActiveStaffRemovableVerifier;
import com.qms.mainservice.domain.service.StoreStaffOverviewCreator;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateOrRemoveActiveStaffUsecase extends Usecase<CreateOrRemoveActiveStaffInput, Void> {

    private final StoreStaffOverviewCreator storeStaffOverviewCreator;
    private final ActiveStaffRemovableVerifier activeStaffRemovableVerifier;
    private final StoreStaffRepository storeStaffRepository;

    @Transactional
    @Override
    public Void execute(CreateOrRemoveActiveStaffInput input) {

        // 変更後の活動フラグがfalseの場合、活動スタッフが削除可能か判定する
        if (input.isActive().isOFF()) {
             activeStaffRemovableVerifier.validate(input.storeId(), input.staffId());
        }

        // 店舗IDを指定して店舗スタッフ状況集約を生成する
        StoreStaffOverview storeStaffOverview = storeStaffOverviewCreator.create(input.storeId());

        // 活動中スタッフを更新する
        storeStaffOverview.toggleActive(input.staffId(), input.isActive());
        storeStaffRepository.deleteAndInsertActiveStaff(storeStaffOverview);

        return null;
    }
}
