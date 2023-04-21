##Steps to Migrate EJB 2.1 with CMT to Spring Beans with transaction support

**This Receipe gives a detailed step by step instruction for migrating EJB 2.1 Stateless Session bean using Container Managed Transaction to Spring Bean**

###1. Analyze the EJB:
   Inspect the EJB code and configuration files to understand the EJB's functionality and transaction attributes.

Example EJB files:

**AccountServiceHome.java** (Home Interface)
```java
import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

public interface AccountServiceHome extends EJBHome {
    AccountService create() throws RemoteException, CreateException;
}
```

**AccountService.java** (Remote Interface)
```java
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface AccountService extends EJBObject {
    void deposit(long accountId, double amount) throws RemoteException;
    void withdraw(long accountId, double amount) throws RemoteException;
}
```

**AccountServiceBean.java** (EJB Implementation)
```java
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

public class AccountServiceBean implements SessionBean {

    public void deposit(long accountId, double amount) {
        // Implementation to deposit the amount
    }

    public void withdraw(long accountId, double amount) {
        // Implementation to withdraw the amount
    }

    // SessionBean methods
    public void ejbCreate() throws CreateException {
    }

    public void ejbRemove() {
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void setSessionContext(SessionContext ctx) {
    }
}
```

**ejb-jar.xml** (Deployment Descriptor)
```xml
<ejb-jar>
  ...
  <enterprise-beans>
    <session>
      <ejb-name>AccountServiceBean</ejb-name>
      ...
      <transaction-type>Container</transaction-type>
    </session>
  </enterprise-beans>
  <assembly-descriptor>
    <container-transaction>
      <method>
        <ejb-name>AccountServiceBean</ejb-name>
        <method-name>deposit</method-name>
      </method>
      <trans-attribute>Required</trans-attribute>
    </container-transaction>
    <container-transaction>
      <method>
        <ejb-name>AccountServiceBean</ejb-name>
        <method-name>withdraw</method-name>
      </method>
      <trans-attribute>RequiresNew</trans-attribute>
    </container-transaction>
  </assembly-descriptor>
</ejb-jar>
```

###2. Create a new Spring Boot project(Optional):
   Generate a new Spring Boot project using Spring Initializr (https://start.spring.io/) with the required dependencies (e.g., Web, JPA, and your preferred database driver).
   Reuse existing spring boot/spring project if available.

###3. Remove Home and Remote interfaces:
   Delete the `AccountServiceHome.java` and `AccountService.java` files, as they are not needed in Spring.

###4. Convert the EJB implementation class to a Spring Bean:
   Rename `AccountServiceBean.java` to `AccountService.java` and add the `@Service` annotation. Remove the `implements SessionBean` part and all SessionBean-related methods. Your class should now look like this:

**AccountService.java**
```java
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public void deposit(long accountId, double amount) {
        // Implementation to deposit the amount
    }

    public void withdraw(long accountId, double amount) {
        // Implementation to withdraw the amount
    }
}
```


###5. Add transaction support:
   Add the `@Transactional` annotation to methods that require transactions, based on the transaction attributes found in the `ejb-jar.xml` file. In this example, the `deposit` method has a transaction attribute of `Required`, and the `withdraw` method has a transaction attribute of `RequiresNew`.

**AccountService.java**
```java
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

@Service
public class AccountService {

    @Transactional(propagation = Propagation.REQUIRED)
    public void deposit(long accountId, double amount) {
        // Implementation to deposit the amount
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void withdraw(long accountId, double amount) {
        // Implementation to withdraw the amount
    }
}
```

###6. Configure the transaction manager:
   In your Spring Boot application, configure the transaction manager by adding the necessary configuration to your `application.properties` or `application.yml` file. For example, if you are using JPA with Hibernate and a MySQL database, your configuration might look like this:

**application.properties**
```
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
```

###7. Update client code:
   Modify the client code that used to look up and use the EJB via JNDI to use the Spring Bean instead. You can now use dependency injection (e.g., `@Autowired`) to inject the `AccountService` bean and call its methods directly.

**AccountController.java**
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/deposit")
    public String deposit(@RequestParam long accountId, @RequestParam double amount) {
        accountService.deposit(accountId, amount);
        return "Amount deposited successfully";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam long accountId, @RequestParam double amount) {
        accountService.withdraw(accountId, amount);
        return "Amount withdrawn successfully";
    }
}
```

###8. Test the application:
   Test your application to ensure that the migrated Spring Bean is working as expected, and transactions are being managed correctly. You may want to use tools like Postman to test the RESTful endpoints.

These steps provide a detailed guide for migrating a Stateless EJB 2.1 using Container-Managed Transactions to a Spring Bean based on Spring Boot.