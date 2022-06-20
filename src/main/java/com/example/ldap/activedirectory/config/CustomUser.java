package com.example.ldap.activedirectory.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {


    private String email;
    private String description;

    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired,
                      boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
                      String email, String description) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

        this.email = email;
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }


}
