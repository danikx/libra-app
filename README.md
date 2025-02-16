# Netcracker Libra Project

Spring Boot application with PostgreSQL database.

## Prerequisites

- Java 17
- Docker and Docker Compose
- Maven (optional, wrapper included)

## Getting Started

### Clone the repository
```bash
git clone <repository-url>
cd netcracker-libra
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
SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/libra_db
SPRING_DATASOURCE_USERNAME: postgres
SPRING_DATASOURCE_PASSWORD: postgres
 ```

## Development
### Database Connection Pool Configuration
The application uses HikariCP with the following settings:

- Maximum pool size: 10
- Minimum idle connections: 5
- Idle timeout: 5 minutes
- Connection timeout: 20 seconds
- Maximum lifetime: 20 minutes
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

## Testing
To run the tests:

```bash
./mvnw test
 ```

## API Documentation
The API documentation will be available at:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI docs: http://localhost:8080/v3/api-docs
## Troubleshooting
1. If the application fails to connect to the database:
   
   - Ensure PostgreSQL container is running: docker-compose ps
   - Check logs: docker-compose logs db
2. If you need to reset the database:
   
   - Stop containers: docker-compose down
   - Delete the postgres-data directory: rm -rf postgres-data
   - Restart: docker-compose up --build

## Swagger UI
- Swagger UI will be available at: http://localhost:8080/swagger-ui.html
- OpenAPI documentation will be at: http://localhost:8080/v3/api-docs
- Each endpoint will have clear documentation
- Response codes and their meanings will be documented
- API consumers will have better understanding of the API capabilities


- Run unit tests (default): mvn test
- Run integration tests only: mvn test -P integration-tests
- Run all tests: mvn test -P unit-tests,integration-tests

## License