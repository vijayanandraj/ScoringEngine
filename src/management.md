In Open Liberty, you can retrieve NamedEndpoint information from the server configuration. You can access the server configuration using `ConfigurationAdmin` service from OSGi.

Here's an example of how to retrieve NamedEndpoint information in Open Liberty:

First, add a reference to the `ConfigurationAdmin` service in your component definition. If you're using Declarative Services (DS), you can do this in your component XML file:

```xml
<reference name="configAdmin"
           interface="org.osgi.service.cm.ConfigurationAdmin"
           policy="static"
           cardinality="1..1" />
```

Next, you can use the `ConfigurationAdmin` service to access the server configuration and retrieve the NamedEndpoint information. Here's an example Java code:

```java
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import java.io.IOException;
import java.util.Dictionary;

public class EndpointInfo {

    private ConfigurationAdmin configAdmin;

    public void setConfigAdmin(ConfigurationAdmin configAdmin) {
        this.configAdmin = configAdmin;
    }

    public void getNamedEndpointInfo() {
        try {
            Configuration[] configurations = configAdmin.listConfigurations("(service.pid=com.ibm.ws.http)");
            for (Configuration configuration : configurations) {
                Dictionary<String, Object> properties = configuration.getProperties();
                String id = (String) properties.get("id");
                String host = (String) properties.get("host");
                String httpPort = (String) properties.get("httpPort");
                String httpsPort = (String) properties.get("httpsPort");

                System.out.println("NamedEndpoint ID: " + id);
                System.out.println("Host: " + host);
                System.out.println("HTTP Port: " + httpPort);
                System.out.println("HTTPS Port: " + httpsPort);
            }
        } catch (IOException | InvalidSyntaxException e) {
            e.printStackTrace();
        }
    }
}
```

This example uses the `ConfigurationAdmin` service to access the server configuration and lists all the `com.ibm.ws.http` configurations, which represent the NamedEndpoints.

Please note that using OSGi services like `ConfigurationAdmin` may require additional dependencies and configurations in your project. Make sure to properly set up your project with OSGi and Open Liberty dependencies.