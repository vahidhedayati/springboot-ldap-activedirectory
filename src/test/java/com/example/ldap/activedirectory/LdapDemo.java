package com.example.ldap.activedirectory;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;
public class LdapDemo {

    public void JNDILookup() {
        String rootFilter = "dc=planetexpress,dc=com";
        String filter = "(&(cn=Philip J. Fry)(uid=fry))";
        String username = "cn=admin,dc=planetexpress,dc=com";
        String password = "GoodNewsEveryone";

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:10389/" + rootFilter);
        env.put(Context.SECURITY_AUTHENTICATION, "simple"); //SIMPLE、SSL/TLS
        env.put(Context.SECURITY_PRINCIPAL, username);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put("filter",filter);
        DirContext ctx = null;
        try {
            ctx = new InitialDirContext(env);
            NamingEnumeration bindings = ctx.listBindings("ou=people");
            while (bindings.hasMore()) {
                Binding bd = (Binding)bindings.next();
                System.out.println(bd.getName() + ": " + bd.getObject());
            }
            System.out.println("---------------------------------- filtered");
            String[] attrIDs = {  "uid", "cn", "userPassword", "sn", "ou", "givenName", "mail", "description", "objectClass"};
            SearchControls ctls = new SearchControls();

            ctls.setReturningAttributes(attrIDs);
            NamingEnumeration e = ctx.search("ou=people", filter, ctls);

            while (e.hasMore()) {
                SearchResult entry = (SearchResult) e.next();
                System.out.println(entry.getName()+" "+entry.getAttributes());

                try {
                    for (NamingEnumeration attr =entry.getAttributes().getAll(); attr.hasMore();) {
                        Attribute attribute= (Attribute) attr.next();
                        System.out.println("Attribute id: " + attribute.getID());

                        for (NamingEnumeration val = attribute.getAll(); val.hasMore();){
                            System.out.println("Attribute value: " + val.next());
                        }

                    }
                } catch (NamingException ee) {
                    ee.printStackTrace();
                }

            }
        } catch (javax.naming.AuthenticationException e) {
            System.out.println("AuthenticationException");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception：");
            e.printStackTrace();
        }finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        LdapDemo ldapJNDI = new LdapDemo();
        ldapJNDI.JNDILookup();

    }
}
