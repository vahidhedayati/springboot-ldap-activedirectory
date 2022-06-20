package com.example.ldap.activedirectory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) -> {
            authz.antMatchers("/").authenticated();
            authz.antMatchers("/admin").authenticated();
            authz.antMatchers("/logs").permitAll();
            authz.anyRequest().authenticated();
        }).formLogin();
        return http.build();
    }


    @Bean
    BindAuthenticator authenticator(BaseLdapPathContextSource contextSource) {

        BindAuthenticator authenticator= new BindAuthenticator(contextSource);
        //authenticator.setUserDnPatterns(new String[]{"uid={0}"});
        authenticator.setUserSearch(new FilterBasedLdapUserSearch("ou=people", "(uid={0})", contextSource));

        //authenticator.setUserDnPatterns(new String[]{"uid={0},dc=example,dc=org"});
        //authenticator.setUserDnPatterns(new String[]{"uid={0},ou=people,dc=planetexpress,dc=com"});
        return authenticator;
    }
    @Bean
    public GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
        SimpleAuthorityMapper simpleMapper = new SimpleAuthorityMapper();
        simpleMapper.setPrefix("ROLE_");

        // this does not work
       // List<GrantedAuthority> authorityList = new ArrayList<>();
       // authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN2"));
       // simpleMapper.mapAuthorities(authorityList);

        simpleMapper.setDefaultAuthority("ROLE_DEFAULT_AUTHORITY");
        //System.out.println("--simpleMapper------ "+simpleMapper.toString());
        return simpleMapper;
    }
    @Bean
    LdapAuthenticationProvider authenticationProvider(LdapAuthenticator authenticator) {
        LdapAuthenticationProvider provider = new LdapAuthenticationProvider(authenticator);


        provider.setUserDetailsContextMapper(new CustomUserDetailsMapper());
        provider.setAuthoritiesMapper(grantedAuthoritiesMapper());
        //System.out.println("---provider----- "+provider.toString());
        //provider.setAuthoritiesMapper((GrantedAuthoritiesMapper)authorityList);

        return provider;
    }
    /*
    @Bean
    AuthenticationManager ldapAuthenticationManager(BaseLdapPathContextSource contextSource) {
        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
        factory.setUserDnPatterns("uid={0},ou=people,dc=planetexpress,dc=com");
        factory.setUserDetailsContextMapper(new PersonContextMapper());
        return factory.createAuthenticationManager();
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.createUser(user);
        return users;
    }
*/

}
