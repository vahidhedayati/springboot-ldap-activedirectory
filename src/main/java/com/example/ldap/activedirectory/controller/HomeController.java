package com.example.ldap.activedirectory.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

    @GetMapping
    public String test(Authentication authentication, Principal principal) {
        String name = principal.getName();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("- name "+name);
        for (GrantedAuthority authority : auth.getAuthorities()) {
            String a = authority.getAuthority();
            //roles.add(a);
            System.out.println("ROLE "+a);
        }

        return "Testing ldap / active directory authentication"+authentication;
    }

    @GetMapping("/logs")
    public String logs() {
        return "No authentication required";
    }

}
