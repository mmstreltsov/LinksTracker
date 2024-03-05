package dataBaseTests;


import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static dataBaseTests.AbstractContainerBaseTest.POSTGRES;

abstract class AbstractContainerBaseTest {
    public final static PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:14")
                    .asCompatibleSubstituteFor("postgres"))
                    .withDatabaseName("scrapper")
                    .withUsername("admin")
                    .withPassword("password");

    static {
        POSTGRES.start();
    }
}

public class IntegrationEnvironmentTest {

    @BeforeAll
    @SneakyThrows
    public static void init() {
        Connection connection = DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        Path pathToChangeLog = Path.of("test","db", "changelog", "db.changelog-master.yaml");
        System.out.println(new File(pathToChangeLog.toString()).exists());
        Liquibase liquibase = new liquibase.Liquibase(pathToChangeLog.toString(), new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());
    }


    @AfterAll
    public static void tearDown() {
        POSTGRES.stop();
    }



    @Test
    @SneakyThrows
    public void test() {
        try (Connection connection = DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM LINKS");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("url"));
            }
        }
    }
}
