# Implementation of CRUD Transaction Rest Api

## Overview

The project implements the logic to visualize a very simple transaction system. It is a maven/DropWizard project and uses Swagger for visual interaction for REST APIs. 

## Prerequisites
- JDK 1.8 (Oracle)
- Maven 3

## Installation 

```
mvn clean package
```

## Deployment
```
java -jar target/transactions-1.0-SNAPSHOT.jar server src/main/resources/config.yaml
```

## Remote Debugging (debug port: 8001)
```
java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8001,suspend=y -jar target/transactions-1.0-SNAPSHOT.jar server src/main/resources/config.yaml
```

## Url to interact with Swagger ( The configuration of the server is in the config.yaml file)
```
http://localhost:9800/swagger
```
