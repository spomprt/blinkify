package com.spomprt.blinkify;

import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class AbstractSpringBootTest {

    private static final Logger log = LoggerFactory.getLogger(AbstractSpringBootTest.class);

    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:15.0")
    );

    static GenericContainer<?> redisContainer = new GenericContainer<>(
            DockerImageName.parse("redis:7.2.3-alpine")
    );

    static {
        postgresContainer.start();
        redisContainer.start();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("cache.redis.host", redisContainer::getHost);
        registry.add("cache.redis.port", () -> redisContainer.getMappedPort(6379).toString());
        registry.add("spring.jpa.show-sql", () -> false);
    }

    @BeforeEach
    void beforeEach() {
        log.info("*** Clean database ***");
    }
}
