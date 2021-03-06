# warehouse-inventory-system
warehouse-inventory-system

Build: .\mvnw.cmd clean install

Create MySQL DB: 
1. Connect to mysql
2. Exectue SQL: 'CREATE DATABASE dev;'
3. Edit file: 'warehouse-inventory-system\src\main\resources\application.properties' spring.datasource.url -> jdbc:mysql://<YOUR_IP/LOCALHOST>:<MYSQL PORT(3306)>/dev
4. spring.datasource.username = <DB_LOGIN_ID>
5. spring.datasource.password = <DB_LOGIN_PW>


Run: mvnw spring-boot:run

Test: import warehouse-inventory-system.postman_collection.json with Postman to test APIs
