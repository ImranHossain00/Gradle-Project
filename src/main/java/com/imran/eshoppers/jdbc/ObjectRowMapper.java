package com.imran.eshoppers.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ObjectRowMapper<E> {
    E mapRowToObject(ResultSet resultSet) throws SQLException;
}
