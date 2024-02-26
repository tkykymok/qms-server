package com.qms.mainservice.infrastructure.config.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomerUserDetails extends User {
    private String name;
    private String email;

    public CustomerUserDetails(String username, Collection<? extends GrantedAuthority> authorities,
                               String name, String email) {
        super(username, "", authorities);

        this.name = name;
        this.email = email;
    }
}
