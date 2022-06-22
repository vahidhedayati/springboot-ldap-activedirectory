package com.example.ldap.activedirectory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Latest spring security has deprecated this way of doing things
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(
                "/logs",
                "/css/**",
                "/images/**",
                "/images**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }

    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //dn: uid=ben,ou=people,dc=springframework,dc=org
        auth
            .ldapAuthentication()
                .userDnPatterns("uid={0}, ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8389/dc=springframework,dc=org")
                .and()
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");
    }
    */

    @Bean
    BindAuthenticator authenticator(BaseLdapPathContextSource contextSource) {

        BindAuthenticator authenticator= new BindAuthenticator(contextSource);
        //authenticator.setUserDnPatterns(new String[]{"cn={0}"});
        authenticator.setUserSearch(new FilterBasedLdapUserSearch("ou=people", "(uid={0})", contextSource));
        return authenticator;
    }
    @Bean
    public GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
        SimpleAuthorityMapper simpleMapper = new SimpleAuthorityMapper();
        simpleMapper.setPrefix("ROLE_");
        simpleMapper.setDefaultAuthority("ROLE_DEFAULT_AUTHORITY");
        return simpleMapper;
    }
    @Bean
    LdapAuthenticationProvider authenticationProvider(LdapAuthenticator authenticator) {
        LdapAuthenticationProvider provider = new LdapAuthenticationProvider(authenticator);

        //This is the default way of doing things
        //provider.setUserDetailsContextMapper(new PersonContextMapper());

        // This is our own customised way of doing above which adds / appends new additional roles to the LDAP user
        // ROLE_ADMIN, ROLE_SUPERUSER, ROLE_LIBRARIAN roles are added to each and every authenticated user
        provider.setUserDetailsContextMapper(new CustomUserDetailsMapper());

        //This is to add ROLE_DEFAULT_AUTHORITY
        provider.setAuthoritiesMapper(grantedAuthoritiesMapper());
        return provider;
    }


}
