package com.imran.eshoppers.jdbc;

import java.sql.SQLException;

@FunctionalInterface
public interface ThrowableConsumer<T> {
    void accept(T t) throws SQLException;
}
