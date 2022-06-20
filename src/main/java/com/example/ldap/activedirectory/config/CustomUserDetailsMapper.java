package com.example.ldap.activedirectory.config;

import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

import javax.naming.directory.Attributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomUserDetailsMapper implements UserDetailsContextMapper {
    private String passwordAttributeName = "userPassword";
    private String rolePrefix = "ROLE_";
    private String[] roleAttributes = null;
    private boolean convertToUpperCase = true;

    private CustomUser userDetails = null;
    private String email = null;
    private String description = null;
    List<GrantedAuthority> authorityList = null;

    public CustomUserDetailsMapper() {
        // TODO Auto-generated constructor stub
    }
    protected GrantedAuthority createAuthority(Object role) {
        if (role instanceof String) {
            if (this.convertToUpperCase) {
                role = ((String) role).toUpperCase();
            }
            return new SimpleGrantedAuthority(this.rolePrefix + role);
        }
        return null;
    }

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
        System.out.println("WE HAVE BEEN CALLED");
        Attributes attributes = ctx.getAttributes();
        try {
            email = (String) attributes.get("mail").get();
            description = (String) attributes.get("description").get();
            //authorityList = (List<GrantedAuthority>) attributes.get("authorities").get();
        } catch (NamingException | javax.naming.NamingException e) {
            e.printStackTrace();
        }
        //authorityList.stream().filter(f-> authorityList.add(f));
        authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorityList.add(new SimpleGrantedAuthority("ROLE_LIBRARIAN"));
        authorityList.add(new SimpleGrantedAuthority("ROLE_SUPERUSER"));
        authorities = authorityList;
        System.out.println("ohooo "+username+authorities);

        CustomUser userDetails = new CustomUser(username, "", true, true, true, true, authorityList, email, description);
        this.userDetails = userDetails;
        return userDetails;
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
        System.out.println("WE HAVE BEEN CALLED 22");
    }

    public CustomUser getUserDetails() {
        return this.userDetails;
    }

}
