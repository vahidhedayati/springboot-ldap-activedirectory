package com.example.ldap.activedirectory;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BindAuthenticatorTest {
    private BindAuthenticator authenticator;
    private Authentication professor;
    private Authentication fry;

    public BaseLdapPathContextSource contextSource(
            @Value("${spring.ldap.urls}") String ldapUrl,
            @Value("${spring.ldap.username}") String bindDn,
            @Value("${spring.ldap.password}") String bindPassword) {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapUrl);
        contextSource.setUserDn(bindDn);
        contextSource.setPassword(bindPassword);
        contextSource.afterPropertiesSet();
        return contextSource;
    }

    //~ Methods ========================================================================================================

    @Before
    public void setUp() {
        authenticator = new BindAuthenticator(contextSource("ldap://localhost:10389/", "cn=admin,dc=planetexpress,dc=com", "GoodNewsEveryone"));
        authenticator.setMessageSource(new SpringSecurityMessageSource());
        professor = new UsernamePasswordAuthenticationToken("Hubert J. Farnsworth", "professor");
        fry = new UsernamePasswordAuthenticationToken("fry", "fry");
    }

    @Test(expected= BadCredentialsException.class)
    public void emptyPasswordIsRejected() {
        authenticator.authenticate(new UsernamePasswordAuthenticationToken("jen", ""));
    }

    @Test
    public void testAuthenticationWithCorrectPasswordSucceeds() {
        authenticator.setUserDnPatterns(new String[] {"cn={0},ou=people,dc=planetexpress,dc=com", "uid={0},ou=people,dc=planetexpress,dc=com"});

        DirContextOperations user = authenticator.authenticate(professor);
        assertEquals("professor", user.getStringAttribute("uid"));
        //authenticator.authenticate(new UsernamePasswordAuthenticationToken("professor", "professor"));
    }

    @Test
    public void testAuthenticationWithWrongPasswordFails() {
        authenticator.setUserDnPatterns(new String[] {"cn={0},ou=people,dc=planetexpress,dc=com", "uid={0},ou=people,dc=planetexpress,dc=com"});
        try {
            authenticator.authenticate(new UsernamePasswordAuthenticationToken("Hubert J. Farnsworth", "wrongpassword"));
            fail("Shouldn't be able to bind with wrong password");
        } catch (BadCredentialsException expected) {}
    }
    @Test
    public void testAuthenticationWithUserSearch() throws Exception {
        //DirContextAdapter ctx = new DirContextAdapter(new DistinguishedName("uid=bob,ou=people"));
        authenticator.setUserSearch(new FilterBasedLdapUserSearch("ou=people,dc=planetexpress,dc=com", "(uid={0})", contextSource("ldap://localhost:10389/", "cn=admin,dc=planetexpress,dc=com", "GoodNewsEveryone")));
        authenticator.afterPropertiesSet();
        authenticator.authenticate(fry);

    }
    @Test
    public void testAuthenticationWithInvalidUserNameFails() {
        authenticator.setUserDnPatterns(new String[] {"cn={0},ou=people,dc=planetexpress,dc=com", "uid={0},ou=people,dc=planetexpress,dc=com"});

        try {
            authenticator.authenticate(new UsernamePasswordAuthenticationToken("nonexistentsuser", "password"));
            fail("Shouldn't be able to bind with invalid username");
        } catch (BadCredentialsException expected) {}
    }

    /*
    @Test
    public void testUserDnPatternReturnsCorrectDn() {
        authenticator.setUserDnPatterns(new String[] {"cn={0},ou=people,dc=planetexpress,dc=com"});
        assertEquals("cn=Hubert J. Farnsworth,ou=people,dc=planetexpress,dc=com", authenticator.getUserDns("Hubert J. Farnsworth").get(0));
    }

     */
}
