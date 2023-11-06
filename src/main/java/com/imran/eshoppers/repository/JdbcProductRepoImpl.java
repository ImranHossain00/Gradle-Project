package com.imran.eshoppers.repository;

import com.imran.eshoppers.domain.Product;
import com.imran.eshoppers.jdbc.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JdbcProductRepoImpl implements ProductRepository{

    private static final Logger LOGGER
            = LoggerFactory.getLogger(JdbcProductRepoImpl.class);

    private DataSource dataSource
            = ConnectionPool.getInstance().getDataSource();

    private static final String SELECT_ALL_PRODUCTS
            = "select * from product";

    public static final String SELECT_PRODUCT_BY_ID
            = "select * from product where id = ?";
    @Override
    public List<Product> findAllProduct() {
        try (var connection = dataSource.getConnection();
             var psmt = connection.prepareStatement(SELECT_ALL_PRODUCTS)) {
            var resultSet = psmt.executeQuery();

            return extractPoducts(resultSet);
        } catch (SQLException e) {
            LOGGER.info("Unable to fetch products from database");
        }
        return Collections.emptyList();
    }

    private List<Product> extractPoducts(ResultSet resultSet) throws SQLException {
        List<Product> products = new ArrayList<>();

        while (resultSet.next()) {
            var product = new Product();
            product.setId(resultSet.getLong("id"));
            product.setName(resultSet.getString("name"));
            product.setVersion(resultSet.getLong("version"));
            product.setDescription(resultSet.getString("description"));
            product.setPrice(resultSet.getBigDecimal("price"));
            product.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());
            product.setDateLastUpdated(resultSet.getTimestamp("date_last_updated").toLocalDateTime());
            products.add(product);
        }
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        try (var connection = dataSource.getConnection();
             var psmt = connection.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            psmt.setLong(1, id);
            var products = extractPoducts(psmt.executeQuery());

            if (products.size() > 0) {
                return Optional.of(products.get(0));
            }
        } catch (SQLException e) {
            LOGGER.info("Unable to fetch product by id: {}", id, e);
        }
        return Optional.empty();
    }

    public void save(Product product) {
        var sql = "INSERT INTO product (name, " +
                "description, " +
                "price, " +
                "version, " +
                "date_created, " +
                "date_last_updated) " +
                "values(?, ?, ?, ?, ?, ?)";

        try (var connection = dataSource.getConnection();
             var pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, product.getName());
            pstm.setString(2, product.getDescription());
            pstm.setBigDecimal(3, product.getPrice());
            pstm.setLong(4, product.getVersion());
            pstm.setTimestamp(5, Timestamp.valueOf(product.getDateCreated()));
            pstm.setTimestamp(6, Timestamp.valueOf(product.getDateLastUpdated()));

            pstm.execute();
        } catch (Exception e) {
            throw new RuntimeException("Unable to insert product information into database");
        }
    }
}
