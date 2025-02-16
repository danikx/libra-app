package kz.netcracker.libra.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.time.Duration;

@TestConfiguration
public class TestConfig implements AutoCloseable {

    static {
        // Initialize Docker environment before container creation
        configureDockerEnvironment();
    }

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withReuse(true)
            .withStartupTimeout(Duration.ofMinutes(5));

    static {
        try {
            postgres
                    .withLogConsumer(outputFrame -> System.out.println("PostgresSql: " + outputFrame.getUtf8String()))
                    .start();
        } catch (Exception e) {
            System.err.println("Failed to start PostgreSQL container: " + e.getMessage());
            throw e;
        }

    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Override
    public void close() {
        if (postgres != null && postgres.isRunning()) {
            postgres.stop();
        }
    }

    private static void configureDockerEnvironment() {
        String environment = System.getProperty("docker.environment", "orbstack"); // Default to OrbStack

        if ("orbstack".equals(environment)) {
            System.setProperty("testcontainers.docker.host", "unix:///var/run/orbstack-docker.sock");
        } else {
            System.setProperty("testcontainers.docker.host", "unix:///var/run/docker.sock");
        }
        System.setProperty("testcontainers.reuse.enable", "true");
    }

}