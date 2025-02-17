# Netcracker Libra Project

Test Spring Boot application with PostgreSQL database for netcracker.

## Prerequisites

- Java 17
- Docker and Docker Compose
- Maven

## Getting Started

### Clone the repository
```bash
git clone git@github.com:danikx/libra-app.git
cd libra-app
```

### Build the application
Using Maven wrapper:

```bash
./mvnw clean package
 ```

 ### Run with Docker Compose
1. Start the application and database:
```bash
docker-compose up --build
 ```

2. The application will be available at:
   - Application: http://localhost:8080
   - Database: localhost:5432

### Database Configuration
PostgreSQL database details:

- Database name: libra_db
- Username: postgres
- Password: postgres
- Port: 5432

### Environment Variables
The following environment variables can be configured in docker-compose.yml:

```yaml
DB_URL: db
DB_PORT: 5432
DB_NAME: libra_db
DB_USERNAME: postgres
DB_PASSWORD: postgres
 ```

## Development

### Useful Docker Commands
Stop the application:

```bash
docker-compose down
 ```

View logs:

```bash
docker-compose logs -f
 ```

Rebuild and restart:

```bash
docker-compose up --build --force-recreate
 ```

### Database Persistence
PostgreSQL data is persisted in the ./postgres-data directory. This ensures your data survives container restarts.

### Testing
To run the unit tests (by default runs only unit tests):

```bash
./mvnw test
 ```

To run the integration tests (Not finished):

```bash
./mvnw -P integration-test
 ```


## API Documentation
The API documentation will be available at:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI docs: http://localhost:8080/v3/api-docs

## Postman
 The Postman collection is available at: postman-collection/Library API.postman_collection.json