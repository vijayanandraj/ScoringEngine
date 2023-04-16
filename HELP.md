# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.9/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.9/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.9/reference/htmlsingle/#web)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.7.9/reference/htmlsingle/#using.devtools)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.9/reference/htmlsingle/#data.sql.jpa-and-spring-data)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

import json
import mysql.connector

# Establish a connection to the MySQL database
connection = mysql.connector.connect(
host="your_host",
user="your_user",
password="your_password",
database="your_database"
)

# Create a cursor object to interact with the database
cursor = connection.cursor()

# Define the SQL query to retrieve the two columns
sql_query = "SELECT AIT_Number, Platform FROM your_table;"

# Execute the SQL query
cursor.execute(sql_query)

# Fetch all the rows returned by the query
rows = cursor.fetchall()

# Close the cursor and the connection
cursor.close()
connection.close()

# Convert the fetched rows into a dictionary with AIT_Number as key and Platform as a list of values
result_dict = {}
for row in rows:
if row[0] in result_dict:
result_dict[row[0]].append(row[1])
else:
result_dict[row[0]] = [row[1]]

# Dump the result_dict into a JSON file
with open("output.json", "w") as output_file:
json.dump(result_dict, output_file, indent=4)
