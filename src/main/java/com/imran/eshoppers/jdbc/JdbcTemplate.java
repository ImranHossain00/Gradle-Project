package com.imran.eshoppers.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(JdbcTemplate.class);
    private DataSource dataSource
            = ConnectionPool.getInstance().getDataSource();

    public void updateQuery(String query, Object... parameters) {
        try (var connection = dataSource.getConnection();
             var pstm = connection.prepareStatement(query)){
            addParameters(pstm, parameters);
            pstm.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Unable to execute update", e);
            throw new RuntimeException("Unable to execute query for result", e);
        }
    }

    public void query(String query, ThrowableConsumer<ResultSet> consumer) {
        try (var connection = dataSource.getConnection();
             var pstm = connection.prepareStatement(query)){

            consumer.accept(pstm.executeQuery());
        } catch (SQLException e) {
            LOGGER.info("Unable to execute query for result", e);
            throw new RuntimeException("Unable to execute query for result", e);
        }
    }

    public long executeInsertQuery(String query,
                                   Object... parameters) {
        try (var connection = dataSource.getConnection();
             var pstm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            addParameters(pstm, parameters);

            final int affectedRows
                    = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet keys = pstm.getGeneratedKeys()){
                if (keys.next())
                    return keys.getLong(1);
                else
                    throw new SQLException("Creating user failed, no ID obtained.");
            }
        } catch (SQLException e) {
            LOGGER.error("Unable to insert query", e);
            throw new RuntimeException(e);
        }
    }

    public <E> List<E> queryForObject(String query,
                                      ObjectRowMapper<E> objectRowMapper) {
        try (var connection = dataSource.getConnection();
             var pstm = connection.prepareStatement(query)){

            var resultSet = pstm.executeQuery(query);
            List<E> listOfE = new ArrayList<>();

            while (resultSet.next()) {
                listOfE.add(
                        objectRowMapper.mapRowToObject(resultSet)
                );
            }

            return listOfE;
        } catch (SQLException e) {
            LOGGER.info("Unable to execute query for result");
            throw new RuntimeException("Unable to execute query for result", e);
        }
    }

    public <E> List<E> queryForObject(String query,
                                      Object param,
                                      ObjectRowMapper<E> objectRowMapper) {
        try (var connection = dataSource.getConnection();
             var pstm = connection.prepareStatement(query)) {
            addParameters(pstm, new Object[]{param});
            var resultSet = pstm.executeQuery();
            List<E> listOfE = new ArrayList<>();

            while (resultSet.next()) {
                listOfE.add(
                        objectRowMapper.mapRowToObject(resultSet)
                );
            }

            return listOfE;
        } catch (SQLException e) {
            LOGGER.info("Unable to execute query for result", e);
            throw new RuntimeException("Unable to execute query for result", e);
        }
    }

    private void addParameters(PreparedStatement pstm,
                               Object[] parameters)
            throws SQLException {

        int idx = 1;
        for (Object parameter : parameters) {
            if (parameter instanceof String) {
                pstm.setString(idx, (String) parameter);
            } else if (parameter instanceof Integer) {
                pstm.setInt(idx, (Integer) parameter);
            } else if (parameter instanceof Long) {
                pstm.setLong(idx, (Long) parameter);
            } else if (parameter instanceof Float) {
                pstm.setFloat(idx, (Float) parameter);
            } else if (parameter instanceof Double) {
                pstm.setDouble(idx, (Double) parameter);
            } else if (parameter instanceof LocalDateTime) {
                pstm.setTimestamp(idx, Timestamp.valueOf((LocalDateTime) parameter));
            } else if (parameter instanceof Blob) {
                pstm.setBlob(idx, (Blob) parameter);
            } else if (parameter instanceof BigDecimal) {
                pstm.setBigDecimal(idx, (BigDecimal) parameter);
            } else if (parameter instanceof Boolean) {
                pstm.setBoolean(idx, (Boolean) parameter);
            }
            idx++;
        }
    }

    public void deleteByID(String query, Long id) {
        try (var connection = dataSource.getConnection();
             var pstm = connection.prepareStatement(query)){

            pstm.setLong(1, id);
            pstm.execute();
        } catch (SQLException e) {
            LOGGER.error("Unable to execute delete by id: {}", id, e);
            throw new RuntimeException("Unable to execute delete", e);
        }
    }
}
