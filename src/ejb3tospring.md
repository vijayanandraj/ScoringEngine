Here's a complete step-by-step guide for migrating an EJB 3.0 Stateless EJB using Container-Managed Transactions to a Spring Boot-based Spring Bean:

1. Analyze the EJB:
   Inspect the EJB code to understand its functionality and transaction attributes.

Example EJB files:

**AccountService.java** (Local Interface)
```java
import javax.ejb.Local;

@Local
public interface AccountService {
    void deposit(long accountId, double amount);
    void withdraw(long accountId, double amount);
}
```

**AccountServiceBean.java** (EJB Implementation)
```java
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
public class AccountServiceBean implements AccountService {

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deposit(long accountId, double amount) {
        // Implementation to deposit the amount
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void withdraw(long accountId, double amount) {
        // Implementation to withdraw the amount
    }
}
```

2. Create a new Spring Boot project:
   Generate a new Spring Boot project using Spring Initializr (https://start.spring.io/) with the required dependencies (e.g., Web, JPA, and your preferred database driver).

3. Convert the EJB implementation class to a Spring Bean:
   Rename `AccountServiceBean.java` to `AccountServiceImpl.java` and add the `@Service` annotation. Your class should now look like this:

**AccountServiceImpl.java**
```java
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    public void deposit(long accountId, double amount) {
        // Implementation to deposit the amount
    }

    public void withdraw(long accountId, double amount) {
        // Implementation to withdraw the amount
    }
}
```

4. Add transaction support:
   Replace the EJB transaction annotations with the Spring `@Transactional` annotation. Map the EJB transaction attributes to the corresponding Spring attributes.

**AccountServiceImpl.java**
```java
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

@Service
public class AccountServiceImpl implements AccountService {

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

5. Configure the transaction manager:
   In your Spring Boot application, configure the transaction manager by adding the necessary configuration to your `application.properties` or `application.yml` file. For example, if you are using JPA with Hibernate and a MySQL database, your configuration might look like this:

**application.properties**
```
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
```

6. Update client code:
   Modify the client code that used to look up and use the EJB via dependency injection to use the Spring Bean instead. You can now use dependency injection (e.g., `@Autowired`) to inject the `AccountService` bean and call its methods directly.


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

7. Test the application:
   Test your application to ensure that the migrated Spring Bean is working as expected, and transactions are being managed correctly. You may want to use tools like Postman to test the RESTful endpoints.

These steps provide a detailed guide for migrating a Stateless EJB 3.0 using Container-Managed Transactions to a Spring Bean based on Spring Boot.
