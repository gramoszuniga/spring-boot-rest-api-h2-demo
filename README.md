
# spring-boot-rest-api-h2-demo

Demo project for a CRUD RESTful web API compliant with the OpenAPI Specification (OAS). Sample data is a collection of Nintendo games and their Metacritic scores.


## Tech Stack

**Server:** Java 17, H2, JPA, Lombok, Spring Boot


## API Reference

#### Read all games

```http
  GET /api/games
```

#### Read game

```http
  GET /api/games/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of game          |

#### Create game

```http
  POST /api/games
```

| Parameter          | Type     | Description                                 |
| :----------------- | :------- | :------------------------------------------ |
| `meta_score`       | `string` | **Required**. Metacritic score of game      |
| `title`            | `string` | **Required**. Title of game                 |
| `platform`         | `string` | **Required**. Platform of game              |
| `date`             | `string` | **Required**. Release date of game          |
| `user_score`       | `string` | **Required**. Metacritic user score of game |
| `link`             | `string` | **Required**. Metacritic link of game       |
| `esrb_rating`      | `string` | **Required**. ESRB rating of game           |

#### Update game

```http
  PUT /api/games/${id}
```

| Parameter          | Type     | Description                                 |
| :----------------- | :------- | :------------------------------------------ |
| `id`               | `string` | **Required**. Id of game                    |
| `meta_score`       | `string` | **Required**. Metacritic score of game      |
| `title`            | `string` | **Required**. Title of game                 |
| `platform`         | `string` | **Required**. Platform of game              |
| `date`             | `string` | **Required**. Release date of game          |
| `user_score`       | `string` | **Required**. Metacritic user score of game |
| `link`             | `string` | **Required**. Metacritic link of game       |
| `esrb_rating`      | `string` | **Required**. ESRB rating of game           |

#### Delete game

```http
  DELETE /api/games/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of game          |


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

