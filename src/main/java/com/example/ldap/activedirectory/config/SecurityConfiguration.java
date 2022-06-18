package com.example.ldap.activedirectory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.userdetails.PersonContextMapper;


@Configuration
public class SecurityConfiguration {
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
    LdapAuthenticationProvider authenticationProvider(LdapAuthenticator authenticator) {
        LdapAuthenticationProvider provider = new LdapAuthenticationProvider(authenticator);
        provider.setUserDetailsContextMapper(new PersonContextMapper());

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
*/
/*
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
