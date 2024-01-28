package com.qms.mainservice.application.usecase.staff;

import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchStaffsUsecase extends Usecase<StoreId, FetchStaffsOutput> {

    @Override
    public FetchStaffsOutput execute(StoreId input) {





        return null;
    }
}
