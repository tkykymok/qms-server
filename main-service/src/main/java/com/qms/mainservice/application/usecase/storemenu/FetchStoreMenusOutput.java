package com.qms.mainservice.application.usecase.storemenu;

import com.qms.mainservice.domain.model.entity.Menu;
import lombok.Builder;

import java.util.List;

@Builder
public record FetchStoreMenusOutput(
        List<Menu> menus
) {
}
