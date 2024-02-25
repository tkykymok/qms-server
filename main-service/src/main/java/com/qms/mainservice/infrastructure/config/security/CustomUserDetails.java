package com.qms.mainservice.infrastructure.config.security;

import com.qms.mainservice.domain.model.valueobject.CompanyId;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Getter
public class CustomUserDetails extends User {
    private CompanyId companyId;
    private List<StoreId> storeIds;
    private String name;
    private String email;

    public CustomUserDetails(String username, Collection<? extends GrantedAuthority> authorities,
                             String companyId, String storeId, String name, String email) {
        super(username, "", authorities);

        this.companyId = Optional.ofNullable(companyId)
                .map(id -> CompanyId.of(Long.valueOf(id)))
                .orElse(null);
        this.storeIds =getStoreIds(storeId);
        this.name = name;
        this.email = email;
    }

    // カンマ区切りのstoreIdをListに変換する
    private List<StoreId> getStoreIds(String storeId) {
        return Optional.ofNullable(storeId)
                .map(str -> {
                    String[] ids = str.split(",");
                    return Arrays.stream(ids)
                            .map(id -> StoreId.of(Long.valueOf(id)))
                            .toList();
                })
                .orElse(null);
    }

    public void validateStoreId(Long storeId) {
        if (storeIds == null || storeIds.stream().noneMatch(id -> id.value().equals(storeId))) {
            throw new IllegalArgumentException("Invalid storeId");
        }
    }

}
