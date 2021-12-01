# Connection pool demo
___
In order to use pooled connections, create an instance of CarambaDataSource,
pass db credentials to the constuctor along with desired database driver name. 
```java
String DB_URL = "jdbc:h2:mem:testdb";
String DB_USER = "sa";
String DB_PASS = "";
String DB_DRIVER = "org.h2.Driver";

DataSource dataSource = new CarambaDataSource(DB_URL, DB_USER, DB_PASS, DB_DRIVER);
```