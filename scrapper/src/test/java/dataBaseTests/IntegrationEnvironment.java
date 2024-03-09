package dataBaseTests;


import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

public abstract class IntegrationEnvironment {
    protected static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14")
            .asCompatibleSubstituteFor("postgres"))
            .withDatabaseName("databaseName")
            .withUsername("username")
            .withPassword("password");

    static {
        container.start();
        initialize();
    }

    @SneakyThrows
    public static void initialize() {
        Connection connection = DriverManager.getConnection(container.getJdbcUrl(), container.getUsername(), container.getPassword());

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        Path pathToChangeLog = Path.of("db", "changelog", "db.changelog-master.yaml");
        Liquibase liquibase = new liquibase.Liquibase(pathToChangeLog.toString(), new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());
    }
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }
}
