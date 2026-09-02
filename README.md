Product CRUD REST API

Tech Stack :-
            Java 21
            Spring Boot 3.2.5
            Spring Data JPA
            Hibernate
            Spring Security
            JWT (JJWT)
            MySQL 8
            H2 Database
            Maven
            Docker
            Swagger/OpenAPI
            Postman

API Endpoints :-    

1) Authentication:-
POST	---->  /api/v1/auth/register	    -----> Register a new user
POST	---->  /api/v1/auth/login	        ----->Login and receive access/refresh tokens
POST	---->  /api/v1/auth/refresh	      ----->Refresh access token using refresh token

2) Products
POST ---->	/api/v1/products	----> ADMIN
GET  ---->	/api/v1/products	----> USER / ADMIN
GET	 ---->  /api/v1/products/{id}	 ----> USER / ADMIN
PUT	 ---->  /api/v1/products/{id}	----> ADMIN
DELETE ---->	/api/v1/products/{id}	 ----> ADMIN


Authentication

1. The API uses JWT Bearer authentication.

2. After logging in, copy the returned access token and include it in requests using:

3. Authorization: Bearer <access-token>

4. Access tokens are short-lived and refresh tokens are used to obtain new access tokens.

5. Refresh token rotation is implemented so that the previous refresh token is revoked when a new refresh token is issued.


Role-Based Authorization :-
  The application supports two roles:
  
  USER — Can view products.
  ADMIN — Can create, update, and delete products.



Pagination :-
The product listing endpoint supports pagination and sorting.

Example:

GET /api/v1/products?page=0&size=10&sort=id,desc


Database :-
MySQL 8 is used as the primary database.
Main entities:
    User
    RefreshToken
    Product
    Item
    
Product and Item entities have a one-to-many



Configure MySQL:- 
      Create the database:
      CREATE DATABASE product_crud_db;
      Update the database username and password in:
      src/main/resources/application.properties


For Running locally use this config for database :- spring.datasource.url=jdbc:mysql://localhost:3306/product_crud_db

For Running in docker container use config for database :- spring.datasource.url=jdbc:mysql://localhost:3306/product_crud_db
In application.properties of src/main/resources/..



The API will be available at:-
      http://localhost:8080

Swagger UI:-
      http://localhost:8080/swagger-ui/index.html

Postman :- Use postman for testing with jwt token, access token, refresh token.

After Running application open postman 1. create account by giving json - username, password, email
                                        2. login :- username, password
                                        3. create product by copying access token and use in authentication Bearer
                                        4. get all products, get by id
                                        5. update by id - shows modified on, modifided by.
                                        6. delete by id.





Running with Docker:-
                      The project includes both a Dockerfile and docker-compose.yml.
                      Build the project:--
                                .\mvnw.cmd clean package -DskipTests
                      Build the Docker image:--
                                docker build -t product-crud-api .
                      Run the application and MySQL using Docker Compose:--
                                docker compose up
                      The API will be available at:
                                http://localhost:8080


Docker Compose:-
                Docker Compose runs:
                MySQL 8
                Product CRUD API


Database Schema Diagram:-
<img width="716" height="805" alt="image" src="https://github.com/user-attachments/assets/7a17a004-3419-4454-b40d-a8450552883a" />
