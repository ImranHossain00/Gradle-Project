package com.imran.eshoppers.repository;

import com.imran.eshoppers.domain.User;
import com.imran.eshoppers.jdbc.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcUserRepoImpl implements UserRepository{

    private static final Logger LOGGER
            = LoggerFactory.getLogger(JdbcUserRepoImpl.class);

    private DataSource dataSource
            = ConnectionPool.getInstance().getDataSource();

    private final static String SAVE_USER
            = "insert into user " +
             " (" +
             " username, " +
             " password, " +
             " version, " +
             " date_created, " +
             " date_last_updated, " +
             " email, " +
             " first_name, " +
             " last_name " +
             " ) " +
             " values(?, ?, ?, ?, ?, ?, ?, ?)";

    private final static String SELECT_BY_USERNAME
            = "select * " +
             " from user where username = ?";

    private final static String SELECT_BY_EMAIL
            = "select * " +
            " from user where email = ?";

//        private final static String SELECT_BY_USERNAME
//            = "select " +
//                " id, " +
//            " username, " +
//            " password, " +
//            " version, " +
//            " date_created, " +
//            " date_last_updated, " +
//            " email, " +
//            " first_name, " +
//            " last_name " +
//            " values(?, ?, ?, ?, ?, ?, ?)";
    @Override
    public void save(User user) {
        try (var connection = dataSource.getConnection();
             var pstm = connection.prepareStatement(SAVE_USER)){
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getPassword());
            pstm.setLong(3, 0L);
            pstm.setTimestamp(4, Timestamp.valueOf(user.getDateCreated()));
            pstm.setTimestamp(5, Timestamp.valueOf(user.getDateLastUpdated()));
            pstm.setString(6, user.getEmail());
            pstm.setString(7, user.getFirstName());
            pstm.setString(8, user.getLastName());

            pstm.execute();
        } catch (SQLException e) {
            LOGGER.info("Unable to save user", e);
            throw new RuntimeException("Unable to save user", e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (var connection = dataSource.getConnection();
             var pstm = connection.prepareStatement(SELECT_BY_USERNAME)){

            pstm.setString(1, username);
            var resultSet
                    = pstm.executeQuery();

            List<User> users
                    = extractUser(resultSet);
            if (!users.isEmpty()) {
                return Optional.of(users.get(0));
            }
        } catch (SQLException e) {
            LOGGER.info("Unable to find by username", e);
            throw new RuntimeException("Unable to find by username", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (var connection = dataSource.getConnection();
             var pstm = connection.prepareStatement(SELECT_BY_EMAIL)){

            pstm.setString(1, email);

            var resultSet = pstm.executeQuery();

            List<User> users
                    = extractUser(resultSet);
            if (!users.isEmpty()) {
                return Optional.of(users.get(0));
            }

        } catch (SQLException e) {
            LOGGER.info("Unable find out user by email", e);
            throw new RuntimeException("Unable find out user by email", e);
        }
        return Optional.empty();
    }

    private List<User> extractUser(ResultSet resultSet) throws SQLException{
        List<User> users
                = new ArrayList<>();

        while (resultSet.next()) {
            var user = new User();
            user.setId(resultSet.getLong("id"));
            user.setVersion(resultSet.getLong("version"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());
            user.setDateLastUpdated(resultSet.getTimestamp("date_last_created").toLocalDateTime());
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setEmail(resultSet.getString("email"));

            users.add(user);
        }

        return users;
    }
}
