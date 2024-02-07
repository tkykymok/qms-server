package com.qms.mainservice.domain.service;

import com.qms.mainservice.domain.model.valueobject.StaffId;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.repository.ActiveStaffRepository;
import com.qms.shared.domain.exception.DomainException;
import com.qms.shared.domain.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActiveStaffRemovableVerifier implements DomainService {

    private final ActiveStaffRepository activeStaffRepository;

    // 店舗IDとスタッフIDを指定して、活動スタッフが削除可能か判定する
    public void validate(StoreId storeId, StaffId staffId) {
        // 対応中の予約が存在し、スタッフが削除不可の場合例外をスローする
        if (!activeStaffRepository.isRemovable(storeId, staffId)) {
            throw new DomainException("対応中の予約が存在するため、スタッフを削除できません");
        }
    }
}
