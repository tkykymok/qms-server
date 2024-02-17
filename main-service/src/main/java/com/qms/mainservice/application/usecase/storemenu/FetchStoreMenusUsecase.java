package com.qms.mainservice.application.usecase.storemenu;

import com.qms.mainservice.domain.model.aggregate.StoreMenus;
import com.qms.mainservice.domain.model.entity.Menu;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.model.valueobject.StoreMenuId;
import com.qms.mainservice.domain.repository.StoreMenusRepository;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FetchStoreMenusUsecase extends Usecase<StoreId, FetchStoreMenusOutput> {

    private final StoreMenusRepository storeMenusRepository;

    @Override
    public FetchStoreMenusOutput execute(StoreId storeId) {
        // 店舗IDを指定して店舗メニュー集約を生成する
        Map<StoreMenuId, Menu> storeMenusMap = storeMenusRepository.findAllByStoreId(storeId);
        List<Menu> menus = storeMenusMap.values().stream().toList();
        StoreMenus.reconstruct(storeId, menus);

        // 店舗メニュー集約を出力用のDTOに変換する
        return FetchStoreMenusOutput.builder()
                .menus(menus)
                .build();
    }
}
