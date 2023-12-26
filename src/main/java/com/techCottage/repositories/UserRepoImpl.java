package com.techCottage.repositories;

import com.techCottage.domain.User;
import com.techCottage.jdbcPool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserRepoImpl implements UserRepo{

    private static final Logger LOGGER
            = LoggerFactory.getLogger(UserRepoImpl.class);
    private DataSource dataSource
            = ConnectionPool.getInstance().getDatasource();

    private final String INSERT_USER
            =  " insert into user("
             + " first_name, "
             + " last_name, "
             + " email, "
             + " password, "
             + " version, "
             + " date_created, "
             + " date_last_updated"
             + ") values (?, ?, ?, ?, ?, ?, ?)";

    private final String FIND_BY_EMAIL
            = " select id from user where email=? ";
    @Override
    public void save(User user) {
        LOGGER.info("saving user {} {}", user.getFirstName(), user.getLastName());
        try (var connection = dataSource.getConnection();
             var prstmnt = connection.prepareStatement(INSERT_USER)){

            prstmnt.setString(1, user.getFirstName());
            prstmnt.setString(2, user.getLastName());
            prstmnt.setString(3,user.getEmail());
            prstmnt.setString(4, user.getPassword());
            user.setVersion(1L);
            prstmnt.setLong(5, user.getVersion());
            prstmnt.setTimestamp(6, Timestamp.valueOf(user.getDateCreated()));
            prstmnt.setTimestamp(7, Timestamp.valueOf(user.getLastDateUpdated()));

            prstmnt.execute();
            LOGGER.info("user {} {} saved into database.", user.getFirstName(), user.getLastName());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to insert data");
        }
    }

    @Override
    public boolean findByEmail(String email) {
        boolean have = false;
        try (var connection = dataSource.getConnection();
             var prstmnt = connection.prepareStatement(FIND_BY_EMAIL)){
            prstmnt.setString(1, email);

            var resultSet = prstmnt.executeQuery();
            if (resultSet.next())
                have = true;
            LOGGER.info("Is there have any matched email ? : {}", have);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to search");
        }

        return have;
    }


}
