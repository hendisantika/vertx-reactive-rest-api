# Vert.x Reactive REST API

[![Java CI with Maven](https://github.com/hendisantika/vertx-reactive-rest-api/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/vertx-reactive-rest-api/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange.svg?logo=openjdk)
[![Vert.x](https://img.shields.io/badge/vert.x-5.2.2-purple.svg)](https://vertx.io)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-336791.svg?logo=postgresql&logoColor=white)
![Dependabot](https://img.shields.io/badge/dependabot-enabled-025E8C.svg?logo=dependabot)

A fully reactive RESTful web service (Books CRUD) built with **Eclipse Vert.x 5** on **JDK 25**, backed by PostgreSQL.
It covers production-ready concerns such as request validation, database migrations, health checks, metrics and logging.

> Originally generated with [start.vertx.io](https://start.vertx.io) and based on the article
> [Vert.x 4: How to build a reactive RESTful Web Service](https://medium.com/@limadelrey/vert-x-4-how-to-build-a-reactive-restful-web-service-dd845e3d0b66),
> now migrated to the Vert.x 5 API.

## Tech Stack

| Component            | Version / Library                                          |
|----------------------|------------------------------------------------------------|
| Language             | Java 25 (`maven.compiler.release=25`)                      |
| Toolkit              | Eclipse Vert.x 5.2.2 (`vertx-stack-depchain`)              |
| Web                  | `vertx-web`, `vertx-web-validation` (JSON Schema Draft 7)  |
| Database             | PostgreSQL 17, `vertx-pg-client`, SQL Client Templates     |
| Migrations           | Flyway 13 (`flyway-core` + `flyway-database-postgresql`)   |
| JSON                 | Jackson (managed by `jackson-bom`)                         |
| Health / Metrics     | `vertx-health-check`, Micrometer + Prometheus              |
| Logging              | SLF4J (`slf4j-simple`)                                     |
| Tests                | JUnit 6, `vertx-junit5`, Testcontainers                    |
| Build                | Maven Wrapper (`./mvnw`)                                   |

## Prerequisites

- JDK 25 (e.g. Temurin 25)
- Docker (for PostgreSQL via `compose.yml`)

## Getting Started

1. Start PostgreSQL:

   ```bash
   docker compose up -d
   ```

   Connection settings live in `src/main/resources/application.properties`
   (`localhost:5432`, database `books`). Flyway runs the migrations and seeds data on startup.

2. Run the tests:

   ```bash
   ./mvnw clean test
   ```

3. Package the application (creates a fat jar):

   ```bash
   ./mvnw clean package
   ```

4. Run the application:

   ```bash
   # from the fat jar
   java -jar target/vertx-reactive-rest-api-1.0.0-SNAPSHOT-fat.jar

   # or straight from Maven
   ./mvnw clean compile exec:java
   ```

   The HTTP server listens on port **8888** by default. Override it with `-Dhttp.port`, e.g.
   `java -Dhttp.port=9090 -jar target/vertx-reactive-rest-api-1.0.0-SNAPSHOT-fat.jar`.

## API Endpoints

| Method   | Path                                | Description                  | Success |
|----------|-------------------------------------|------------------------------|---------|
| `GET`    | `/api/v1/books?page=1&limit=20`     | List books (paginated)       | `200`   |
| `GET`    | `/api/v1/books/{id}`                | Get a book by id             | `200`   |
| `POST`   | `/api/v1/books`                     | Create a book                | `201`   |
| `PUT`    | `/api/v1/books/{id}`                | Update a book                | `200`   |
| `DELETE` | `/api/v1/books/{id}`                | Delete a book                | `204`   |
| `GET`    | `/health`                           | Health check (incl. database)| `200`   |
| `GET`    | `/metrics`                          | Prometheus metrics           | `200`   |

Errors are returned as JSON, e.g. `{"error": "No book with id 99999"}` with `400` (invalid request) or `404` (not found).

Example request:

```bash
curl -X POST http://localhost:8888/api/v1/books \
  -H 'Content-Type: application/json' \
  -d '{
        "author": "Chinua Achebe",
        "country": "Nigeria",
        "image_link": "images/things-fall-apart.jpg",
        "language": "English",
        "link": "https://en.wikipedia.org/wiki/Things_Fall_Apart",
        "pages": 209,
        "title": "Things Fall Apart",
        "year": 1958
      }'
```

## Project Structure

```
src/main/java/id/my/hendisantika/vertx_reactive_rest_api
├── Main.java                 # entry point: Vert.x + Micrometer/Prometheus, deploys MainVerticle
├── verticle/                 # MainVerticle, MigrationVerticle (worker), ApiVerticle (HTTP server)
├── api/router/               # Book, health check and metrics routes
├── api/handler/              # request handlers, validation (JSON Schema) and error handling
├── api/service/              # business logic, transactions, pagination
├── api/repository/           # SQL Client Templates queries
├── api/model/                # entity & response DTOs
└── utils/                    # config, DB, query, response and log helpers
src/main/resources
├── application.properties    # datasource configuration
└── db/migration/             # Flyway migrations
```

## CI/CD & Dependency Management

- **GitHub Actions** — [`.github/workflows/maven.yml`](.github/workflows/maven.yml) builds and tests with Temurin JDK 25
  (`mvn -B verify`) on every push and pull request to `main`, then submits the dependency graph.
- **Dependabot** — [`.github/dependabot.yml`](.github/dependabot.yml) keeps dependencies fresh:
  - **Maven** (daily, 05:00 Asia/Jakarta) grouped into `vertx`, `jackson`, `flyway-postgres`, `observability`,
    `testing` and `maven-plugins`
  - **GitHub Actions** (weekly) grouped into a single PR
  - **Docker Compose** (weekly) for the PostgreSQL image in `compose.yml`
  - PRs are labeled, assigned and use conventional commit prefixes (`chore(deps)`, `ci(deps)`, `chore(docker)`)

## Vert.x 5 Migration Notes

The codebase was migrated from Vert.x 4 to Vert.x 5. Highlights:

- `PgPool` → `Pool` created with `PgBuilder`
- Vert.x internal logging → SLF4J
- `SchemaParser`/`SchemaRouter` → `SchemaRepository` + `ValidationHandlerBuilder`
- `router.mountSubRouter(...)` → `router.route("/api/v1/*").subRouter(...)`
- `rc.getBodyAsJson()` → `rc.body().asJsonObject()`
- `DeploymentOptions.setWorker(true)` → `setThreadingModel(ThreadingModel.WORKER)`
- Callback APIs (`listen`, `getConnection`) → `Future` API
- Micrometer registry passed via `Vertx.builder().withMetrics(new MicrometerMetricsFactory(registry))`
- `io.vertx.core.Launcher` was removed, so the fat jar now starts from `Main`

## Background: Why Vert.x?

Eclipse Vert.x is a toolkit for building reactive applications on the JVM. Reactive applications are both scalable as
workloads grow and resilient when failures arise, making them ideal for low-latency and high-throughput use cases.
Instead of blocking a thread when an I/O operation occurs, Vert.x uses **event loops**: it moves on to the next event
that is ready to progress and resumes the initial one when its callback's turn comes. As long as the event loop is not
blocked, this yields a significant performance improvement.

<p align="center"><img width="50%" src="https://cdn-images-1.medium.com/max/720/1*fhQ96tDLMd_zonV_FuVwGA.png" alt="Event loop"/></p>

The codebase follows a traditional N-tier architecture:

- **Router** — finds the matching route for an HTTP request; each route chains a Logger handler, a Web Validation
  handler and a business-logic handler, versioned by URI path (`/api/v1`).
- **Handler** — extracts query/path parameters and body, then returns a JSON response with the appropriate status code.
- **Service** — business logic, transactional control, DTO mapping and pagination using the non-blocking reactive
  PostgreSQL client.
- **Repository** — encapsulates data access with SQL Client Templates to map rows to entities.
- **Verticles** — *standard* verticles run on an event loop; *worker* verticles run on a worker pool and are used for
  blocking work such as Flyway migrations.
- **Health checks** — expose whether the service (and its database connection) is healthy, useful for liveness and
  readiness probes.
- **Metrics** — Micrometer metrics exposed in Prometheus format, ready to be scraped and visualised in Grafana.
- **Tests** — `vertx-junit5`, with Testcontainers available to mimic PostgreSQL.

<p align="center"><img width="50%" src="https://cdn-images-1.medium.com/max/720/1*AnNnl6EKV4BjjQlu1Nu94g.png" alt="Package structure"/></p>

## References

- [Vert.x Documentation](https://vertx.io/docs/)
- [Vert.x 5 migration guide](https://vertx.io/docs/guides/vertx-5-migration-guide/)
- [Original article (Vert.x 4)](https://medium.com/@limadelrey/vert-x-4-how-to-build-a-reactive-restful-web-service-dd845e3d0b66)
- [Vert.x Stack Overflow](https://stackoverflow.com/questions/tagged/vert.x?sort=newest&pageSize=15)
- [Vert.x Discord](https://discord.gg/6ry7aqPWXy)
