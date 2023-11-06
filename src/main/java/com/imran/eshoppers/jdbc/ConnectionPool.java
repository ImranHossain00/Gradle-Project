package com.imran.eshoppers.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class ConnectionPool {
    private static final ConnectionPool INSTANCE
            = new ConnectionPool();

    private ConnectionPool() {

    }

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    public DataSource getDataSource() {
        var dbProp = ResourceBundle.getBundle("db");
        var config = new HikariConfig();
        config.setJdbcUrl(dbProp.getString("db.url"));
        config.setUsername(dbProp.getString("db.user"));
        config.setPassword(dbProp.getString("db.password"));
        config.setDriverClassName(dbProp.getString("db.driver"));
        config.setMaximumPoolSize(Integer.parseInt(dbProp.getString("db.max.connections")));

        return new HikariDataSource(config);
    }
}
