# springboot-ldap-activedirectory

There are two branches: 
```
git clone git@github.com:vahidhedayati/springboot-ldap-activedirectory.git -b main {default if -b not set}

git clone git@github.com:vahidhedayati/springboot-ldap-activedirectory.git -b deprecated
```

# [youtube video](https://studio.youtube.com/video/LLyE8p_UcxM/edit)
Video suggests authenticating via cn rather than UID or username. This has since been fixed in latest updates 

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




### Start the site
```
 mvn spring-boot:run
```

### Ensure LDAP is running 
```
docker run --rm -p 10389:10389 -p 10636:10636 rroemhild/test-openldap
```


Goto `http://localhost:8080/` you should be able to authenticate using UID password i.e. username: fry password: fry


## LDAP/AD Configuration
The current [username in application.yml](https://github.com/vahidhedayati/springboot-ldap-activedirectory/blob/main/src/main/resources/application.yml#L4) is `cn=admin,dc=planetexpress,dc=com`
in the world of Active Directory the username may actually be something like `workgroup\\username`
