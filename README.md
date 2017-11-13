# Web-service-for-Communicator
Backend for android app.<br />
Made with Spring Boot so it is able too run out of the box. To do that use Maven Build with goal: spring-boot:run.<br />
Self contained file with database schema is included.<br />
<br />
Befor starting a service check src\main\resources for proper settings. 
First change db_template.properties and other_template.properties to db.properties and other.properties, 
then fill up those files with your settings for database connection, jwt secret key and fcm server key.<br />
- JWT secret key can be any string, but a point is that it should be long, random and above else secret<br />
- FCM server key you can find here https://console.firebase.google.com/ (go to Your project -> Settings -> Cloud Messaging) if you don't have a project crate one, you need it for apps to work, at least for now.
<br />
TODO <br />
- administrator panel(as a separate service)
- replace FCM with own solution