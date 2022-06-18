# springboot-ldap-activedirectory

There are two branches: 
```
git clone git@github.com:vahidhedayati/springboot-ldap-activedirectory.git -b main {default if -b not set}

git clone git@github.com:vahidhedayati/springboot-ldap-activedirectory.git -b deprecated
```

# [youtube video](https://studio.youtube.com/video/LLyE8p_UcxM/edit)

```bash
spring init --name=springboot-ldap-activedirectory --packageName=com.example.ldap.activedirectory --dependencies=web,security springboot-ldap-activedirectory
```

Add the following to  `pom.xml`
```xml
	<dependencies>
    <!-- ........... add to dependencies block -->

		<dependency>
			<groupId>org.springframework.ldap</groupId>
			<artifactId>spring-ldap-core</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-ldap</artifactId>
		</dependency>
</dependencies>
```
Goto `https://spring.io/guides/gs/authenticating-ldap/`
Scroll down to : `Set up User Data` and copy content provided into `resources/test-server.ldif`

Launch site and goto `http://localhost:8080`, the following authentication worked



https://github.com/rroemhild/docker-test-openldap


```
docker pull rroemhild/test-openldap
docker run --rm -p 10389:10389 -p 10636:10636 rroemhild/test-openldap
Testing
# List all Users
ldapsearch -H ldap://localhost:10389 -x -b "ou=people,dc=planetexpress,dc=com" -D "cn=admin,dc=planetexpress,dc=com" -w GoodNewsEveryone "(objectClass=inetOrgPerson)"


LDAP structure
dc=planetexpress,dc=com
Admin	Secret
cn=admin,dc=planetexpress,dc=com	GoodNewsEveryone

ou=people,dc=planetexpress,dc=com


cn=Hubert J. Farnsworth,ou=people,dc=planetexpress,dc=com


uid	professor
userPassword	professor




cn=Philip J. Fry,ou=people,dc=planetexpress,dc=com

uid	fry
userPassword	fry

cn=John A. Zoidberg,ou=people,dc=planetexpress,dc=com
id	zoidberg
userPassword	zoidberg
```




