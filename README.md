
# spring-boot-rest-api-h2-demo

Demo project for a CRUD RESTful web API compliant with the OpenAPI Specification (OAS). Sample data is a collection of Nintendo games and their Metacritic scores.


## Tech Stack

**Server:** Java 17, H2, JPA, Lombok, Spring Boot


## API Reference

- [https://gramoszuniga.github.io/spring-boot-rest-api-h2-demo-swagger-page/](https://gramoszuniga.github.io/spring-boot-rest-api-h2-demo-swagger-page/)


## Run Locally

Clone the project

```bash
  git clone https://github.com/gramoszuniga/spring-boot-rest-api-h2-demo
```

Go to the project directory

```bash
  cd spring-boot-rest-api-h2-demo
```

Install dependencies

```bash
  mvn clean install
```

Start the server

```bash
  mvn package
  java -jar target/spring-boot-rest-api-h2-demo-1.0.0.jar
```


## Acknowledgements

 - [Nintendo Games Metacritic Data](https://github.com/yaylinda/nintendo-games-ratings)

