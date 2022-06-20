package com.example.ldap.activedirectory.controller;

import com.example.ldap.activedirectory.config.CustomUser;
import com.example.ldap.activedirectory.config.CustomUserDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private CustomUserDetailsMapper userDetailsMapper;


    @GetMapping
    public String test(Authentication authentication, Principal principal) {

        String name = principal.getName();

        List<String> roles = new ArrayList<String>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        CustomUser customUser = userDetailsMapper.getUserDetails();
        System.out.println("- name "+name);
        for (GrantedAuthority authority : auth.getAuthorities()) {
            String a = authority.getAuthority();
            roles.add(a);
            System.out.println("ROLE "+a);
        }

        return "Testing ldap / active directory authentication"+authentication;
    }

    @GetMapping("/logs")
    public String logs() {
        return "No authentication required";
    }

}
