package ua.com.alevel.config.jpa.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.DatasourceProperties;
import ua.com.alevel.config.jpa.JpaConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class MySqlJpaConfig implements JpaConfig {

    private final DatasourceProperties datasourceProperties;
    private Connection connection;
    private Statement statement;

    public MySqlJpaConfig(DatasourceProperties datasourceProperties) {
        this.datasourceProperties = datasourceProperties;
    }

    @Override
    public void connect() {
        try {
            Class.forName(datasourceProperties.getDriverClassName());
            connection = DriverManager.getConnection(
                    datasourceProperties.getUrl(),
                    datasourceProperties.getUsername(),
                    datasourceProperties.getPassword());
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Statement getStatement() {
        return statement;
    }
}
