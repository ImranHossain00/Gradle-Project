package com.techCottage.jdbcPool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

// To create a pool where connections are stored instead of being destroyed,
// and new connections are generated if the maximum connections are currently in use.
// This is a singleton design patter.
public class ConnectionPool {
    private static final ConnectionPool INSTANCE
            = new ConnectionPool();
    private ConnectionPool(){}

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    public DataSource getDatasource() {
        // We utilize the ResourceBundle class
        // for convenient access to data from the db.properties file.
        var dbProb = ResourceBundle.getBundle("db");

        // Configuring the database connection involves utilizing a connection pool.
        // In this context, we use a built-in connection pool object named HikariConfig.
        var config = new HikariConfig();
        config.setJdbcUrl(dbProb.getString("db.url"));
        config.setUsername(dbProb.getString("db.user"));
        config.setPassword(dbProb.getString("db.password"));
        config.setDriverClassName(dbProb.getString("db.driver"));
        var maxPoolSize = dbProb.getString("db.max.connections");
        config.setMaximumPoolSize(Integer.parseInt(maxPoolSize));

        return new HikariDataSource(config);
    }
}
