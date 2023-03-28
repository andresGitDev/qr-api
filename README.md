# QR-code Generator and Reader

## Application used [Java 1.8]

###### Spring boot application exposing REST API endpoint to genrate QR-code representing custom message and another endpoint to read the decoded message, built using Java [Spring boot] and [google's zxing library](https://opensource.google/projects/zxing).

<center>
	<a target='_blank' href='https://spring-boot-qr-code-generator.herokuapp.com/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config'>Running Application</a>
</center>

## Local Setup

* Install [Java 1.8]
* Install [Maven](https://onurdesk.com/what-is-maven-plugin/)

Run the below commands in the core

```
mvn clean install
```

```
mvn spring-boot:run

```

server port is configured to 9090 which can be changed in application.properties file

Go to the below url to view swagger-ui (API docs)

```
http://localhost:9090/qr-api/swagger-ui.html
http://testing.betasepp.com.ar:9110/qr-api/swagger-ui.html
```
