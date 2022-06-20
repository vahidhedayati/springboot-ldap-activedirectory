package com.example.ldap.activedirectory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.odm.core.ObjectDirectoryMapper;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpringbootLdapActivedirectoryApplicationTests {

	@Autowired
	private LdapTemplate ldapTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	public void authenticateFry() {
		ContextSource contextSource=ldapTemplate.getContextSource();
		Filter filter = new EqualsFilter("uid", "fry");
		boolean auth = ldapTemplate.authenticate("ou=people",   filter.encode(), "fry");
		assertEquals(true, auth);
	}

	@Test
	public void findAll() throws Exception {
		/**
		 * LdapContextSource contextSource = new LdapContextSource();
		 * contextSource.setUrl("ldap://adserver.mycompany.com:3268");
		 * contextSource.setBase("DC=AD,DC=MYCOMPANY,DC=COM");
		 * contextSource.setUserDn("readonlyuser@ad.mycompany.com");
		 * contextSource.setPassword("password1");
		 * contextSource.afterPropertiesSet();
		 *
		 * LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
		 * ldapTemplate.afterPropertiesSet();
		 *
		 * // Perform the authentication.
		 * Filter filter = new EqualsFilter("sAMAccountName", "mpilone");
		 *
		 * boolean authed = ldapTemplate.authenticate("OU=CorpUsers",
		 *     filter.encode(),
		 *     "user-entered-password");
		 *

		LdapContextSource contextSource = new LdapContextSource();
		 contextSource.setUrl("ldap://localhost:10389");
		  contextSource.setBase("dc=planetexpress,dc=com");
		  contextSource.setUserDn("cn=admin,dc=planetexpress,dc=com");
		  contextSource.setPassword("GoodNewsEveryone");
		  contextSource.afterPropertiesSet();

		  LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
		  ldapTemplate.afterPropertiesSet();
		 */

		ContextSource contextSource=ldapTemplate.getContextSource();


		List<String> str=ldapTemplate.listBindings("ou=people");
		System.out.println(str);

		ObjectDirectoryMapper objectDirectoryMapper=ldapTemplate.getObjectDirectoryMapper();
		//List<String> dc=ldapTemplate.list("objectClass=*");
		//System.out.println(dc);
		//LdapQuery query = query().base("dc=pub,dc=org").where("objectclass").is("*");
		//List<Object> obj=ldapTemplate.search(query, (AttributesMapper<Object>) attributes -> attributes);
		//System.out.println(dc);
		//DirContext dirContext=contextSource.getReadOnlyContext();
		//Hashtable hashtable=dirContext.getEnvironment();
		//System.out.println(hashtable);
		//ldapTemplate.bind();
		//DirContext readWriteContext = contextSource.getReadWriteContext();
		//SearchControls controls = new SearchControls();
		//controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		//NamingEnumeration<SearchResult> search = readWriteContext.search("", "(objectclass=person)", controls);
		//ldapTemplate.search("ou=persons", "&(objectclass=inetOrgPerson)(cn=abc)", new AttributesMapper<Object>() {
		//    @Override
		//    public Object mapFromAttributes(Attributes attributes) throws NamingException {
		//        return attributes.get("cn").get();
		//    }
		//});

		//List<String> cns=ldapTemplate.search("", "objectclass=inetOrgPerson", (AttributesMapper<String>) attributes -> attributes.get("cn").get().toString());
		//ldapTemplate.listBindings(LdapUtils.emptyLdapName(),
		//        new AbstractContextMapper<Object>() {
		//            @Override
		//            protected Object doMapFromContext(DirContextOperations ctx) {
		//                Name dn = ctx.getDn();
		//                System.out.println(dn);
		//                return null;
		//            }
		//        });
		System.out.println(new LdapShaPasswordEncoder().encode("fry"));
	}

}
