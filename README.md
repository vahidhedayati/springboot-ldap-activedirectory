#springboot-ldap-activedirectory



```bash
spring init --name=springboot-ldap-activedirectory --packageName=com.example.ldap.activedirectory --dependencies=web,security springboot-ldap-activedirectory
```

Add the following to  `pom.xml`
```xml
	<dependencies>
    <!-- ........... add to dependencies block -->
	<dependency>
			<groupId>com.unboundid</groupId>
			<artifactId>unboundid-ldapsdk</artifactId>
		</dependency>
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

```
username: bob
password: bobspassword
```
