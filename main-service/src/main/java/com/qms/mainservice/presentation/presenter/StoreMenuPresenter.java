package com.qms.mainservice.presentation.presenter;

import com.qms.mainservice.application.usecase.storemenu.FetchStoreMenusOutput;
import com.qms.mainservice.presentation.web.response.storemenu.GetStoreMenus;
import com.qms.mainservice.presentation.web.response.storemenu.StoreMenuResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class StoreMenuPresenter {
    public ResponseEntity<GetStoreMenus> present(FetchStoreMenusOutput output) {
        var response = GetStoreMenus.builder()
                .storeMenus(output.menus().stream()
                        .map(menu -> StoreMenuResponse.builder()
                                .storeMenuId(menu.getKey().storeMenuId().value())
                                .menuName(menu.getMenuName().value())
                                .price(menu.getPrice().value())
                                .time(menu.getTime().value())
                                .tagColor(menu.getTagColor().getHexCode())
                                .disabled(menu.getDisabled().value())
                                .build())
                        .toList()
                )
                .build();

        return ResponseEntity.ok(response);
    }
}
