Auctions Web App
================
This application is a simple web application that allows users to create auctions and bid on them. The application is built using Spring Boot and React.

## Running the application

To run the application, you need to have Java 11, Node.js and PostgreSQL installed on your machine. You can run the application by doing the following:

1. Create a database in PostgreSQL called `auctions` and update the `application.properties` file with your database credentials.

2. execute : 

```
cd .../com.example.auctions
./mvnw spring-boot:run
```

 3. For the frontend go to 
```
cd .../frontend
npm start
```

# Application structure
The application is structured as follows:
- `com.example.auctions` - the backend application (Based on Spring Boot)
- `frontend` - the frontend application (Based on React)

Backend
=======

The backend application is a Spring Boot application that exposes a REST API for managing auctions. The application uses a PostgreSQL database to store the data.

The application has the following packages:
- `com.example.auctions.controller` - contains the REST controllers
- `com.example.auctions.service` - contains the services
- `com.example.auctions.repository` - contains the repositories
- `com.example.auctions.model` - contains the entities
- `com.example.auctions.exception` - contains the exception handlers
- `com.example.auctions.security` - contains the security configuration
- `com.example.auctions.dto` - contains the data transfer objects

## Model
The application has the following entities:
- `User` - represents a user (id, username, password, email, roles)
- `Auction` - represents an auction (currently just id, name and owner)
- `Role` - represents a role (id, name)

## DTOs
DTOs are used to transfer data between the frontend and the backend without exposing the entities directly. The application has the following DTOs:
- `UserDTO` - represents a user (id, name, username, email, password)
- `AuctionDTO` - represents an auction (id, name, ownerId, ownerUsername)
- `LoginDTO` - represents the login request (username, password)
- `RegisterDTO` - represents the register request (username, name, email, password)\
- `JwtAuthResponse` - represents the JWT authentication response (used to send the JWT token to the client) (accessToken, tokenType, role)

## Repository
The application uses Spring Data JPA repositories in order to interact with the database. 
Since we are using Spring Boot, we don't need to implement the repositories manually, we just need to define interfaces that extend the `JpaRepository` interface.
The application has the following repositories:
- `UserRepository` - used to interact with the `User` entity
- `AuctionRepository` - used to interact with the `Auction` entity
- `RoleRepository` - used to interact with the `Role` entity

## Service
The service layer contains the business logic of the application. The services are used to interact with the repositories and perform the necessary operations.\
Service interfaces are defined in the `Service` package and the implementations are defined in the `Service.impl` package.
We do this because we want to separate the interface from the implementation, so that we can easily switch the implementation if needed.
The application has the following services: 
- `UserService` - contains the business logic for managing users (implementation - `UserServiceImpl`)
- `AuctionService` - contains the business logic for managing auctions (implementation - `AuctionServiceImpl`)
- `AuthService` - contains the business logic for authentication and registration (implementation - `AuthServiceImpl`)

## Controller
The controller layer contains the REST controllers that handle the incoming requests and return the responses.
The controllers are responsible for mapping the incoming requests to the appropriate service methods and returning the responses.
The application has the following controllers:
- `UserController` - contains the endpoints for managing users
- `AuctionController` - contains the endpoints for managing auctions
- `AuthController` - contains the endpoints for authentication and registration

## Exceptions
The application has exception handlers that handle the exceptions thrown by the application and return the appropriate response.
The application has the following exception handlers:
- `GlobalExceptionHandler` - handles the exceptions thrown by the application and returns the appropriate response
- `ResourceNotFoundException` - handles the `ResourceNotFoundException` and returns the appropriate response
- `Error Details` - contains the error details (timestamp, message, details)
- `APIException` - contains the API exception (message, status, timestamp)

# Security
The application uses JWT (JSON Web Token) for authentication. When a user logs in, the backend generates a JWT token and sends it back to the client.
The client then includes this token in the headers of the subsequent requests to authenticate the user.
The application has the following security configuration:
- `SpringSecurityConfig` - contains the security configuration (JWT filter, authentication manager, password encoder)
- `JwtAuthenticationEntryPoint` - handles the authentication exceptions and returns the appropriate response
- `JwtTokenProvider` - generates and validates JWT tokens (creates the token, extracts the username from the token, validates the token)





Frontend
========

The frontend application is a React application that allows users to interact with the backend application.
The application uses Vite.js as the build tool and Bootstrap for the styling (at the moment).

[//]: # (new line)
The base application has been created using the following command: 
npm create vite@latest auctions-frontend

[//]: # (new line)
The following libraries have been added to the application:
- `react-router-dom` - for routing
- `axios` - for making HTTP requests
- `react-bootstrap` - for styling

[//]: # (new line)
## Structure
The application is structured as follows:
- `src/components` - contains the components of the application
- `src/services` - contains the services used to interact with the backend

## Components
The components are used to build the UI of the application.
Each component is responsible for a specific part of the UI and can be reused in different parts of the application.

[//]: # (new line)
The application has the following components:
- `ListAuctionsComponent` - displays the list of auctions
- `AuctionComponent` - currently displays the form to add or edit an auction 
- `HeaderComponent` - displays the header of the application (logo and navigation)
- `FooterComponent` - displays the footer of the application (copyright)
- `UserComponent` - displays the user information along with their auctions
- `LoginComponent` - displays the login form
- `RegisterComponent` - displays the register form

## Services
The services are used to interact with the backend application. 
Each service is responsible for making HTTP requests to the backend and/or handling the responses.

[//]: # (new line)
The application has the following services:
- `AuctionService` - interacts with the `AuctionController` to manage auctions
- `AuthService` - interacts with the `AuthController` to handle authentication and registration
- `UserService` - interacts with the `UserController` to manage users