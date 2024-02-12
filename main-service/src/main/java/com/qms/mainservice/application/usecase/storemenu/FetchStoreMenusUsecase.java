package com.qms.mainservice.application.usecase.storemenu;

import com.qms.mainservice.domain.model.aggregate.StoreMenus;
import com.qms.mainservice.domain.model.entity.Menu;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.repository.StoreMenuRepository;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FetchStoreMenusUsecase extends Usecase<StoreId, FetchStoreMenusOutput> {

    private final StoreMenuRepository storeMenuRepository;

    @Override
    public FetchStoreMenusOutput execute(StoreId storeId) {
        // 店舗IDを指定して店舗メニュー集約を生成する
        List<Menu> menus = storeMenuRepository.findAllByStoreId(storeId);
        StoreMenus.reconstruct(storeId, menus);

        // 店舗メニュー集約を出力用のDTOに変換する
        return FetchStoreMenusOutput.builder()
                .menus(menus)
                .build();
    }
}
