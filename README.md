# Digital Library Application

This repository contains a simple digital library that maintain books and leasing of the books. 

## Prerequisites 

Before you begin, ensure you have the following installed: 

* [Java Development Kit JDK](https://www.oracle.com/za/java/technologies/downloads/) (version 8 or later)
* [Maven](https://maven.apache.org/download.cgi)

## Getting Started 

1. Clone this repository to your local machine: 

* `git clone https://gitlab.com/thoughtprocess2/spring-start-up.git`

## Connect to H2
1. H2 configuration is in the application.properties
2. When the server is running navigate to http://localhost:8082/login.jsp?
3. Ensure Saved Settings = Generic H2 (Embedded) and Setting Name = Generic H2 (Embedded)
4. Driver Class = org.h2Driver
5. JDBC URL = jdbc:h2:mem:library
6. userName and password is in the application.properties 

## Navigating the application
1. Server will start on port: 8081
2. Navigate to http://localhost:8081/ 

## How to run application test
To run the Maven tests (Having Maven installed), execute the following command in your terminal/command prompt:
1. Run the Application.
2. Run in terminal.

`mvn test`

## Project Structure
1. Navigate to the project directory: src -> main -> java -> com -> thoughtprocess -> Application.java
2. Postman collection in root directory of the -> digital-library.postman_collection.json
