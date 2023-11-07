package com.imran.eshoppers.repository;

import com.imran.eshoppers.domain.CartItem;
import com.imran.eshoppers.exception.CartNotFoundException;
import com.imran.eshoppers.exception.OptimistickLockingFailureException;
import com.imran.eshoppers.jdbc.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCartItemRepoImpl implements CartItemRepository{

    private static final Logger LOGGER
            = LoggerFactory.getLogger(JdbcCartItemRepoImpl.class);
    private DataSource dataSource
            = ConnectionPool.getInstance().getDataSource();

    private static final String INSERT_CART_ITEM
            = "insert into cart_item (" +
             " quantity, " +
             " price, " +
             " product_id, " +
             " version, " +
             " date_created, " +
             " date_last_updated " +
             ") " +
             " values (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_CART_ITEM
            = "update cart_item " +
             " set quantity = ?, " +
             " price = ?, " +
             " version = ?, " +
             " date_last_updated = ? where id = ? " ;

    private static final String SELECT_CART_ITEM
            = "select * from cart_item where id = ?";

    private static final String DELETE_CART
            = "delete from cart_item where id = ?";
    @Override
    public CartItem save(CartItem cartItem) {
        try (var connection = dataSource.getConnection();
             var pstm = connection.prepareStatement(INSERT_CART_ITEM,
                     Statement.RETURN_GENERATED_KEYS)){

            pstm.setInt(1, cartItem.getQuantity());
            pstm.setBigDecimal(2, cartItem.getPrice());
            pstm.setLong(3, cartItem.getProduct().getId());
            pstm.setLong(4, 0L);
            pstm.setTimestamp(5, Timestamp.valueOf(cartItem.getDateCreated()));
            pstm.setTimestamp(6, Timestamp.valueOf(cartItem.getDateLastUpdated()));

            int affectedRows = pstm.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstm.getGeneratedKeys()){
                if (generatedKeys.next()) {
                    long cartItemId = generatedKeys.getLong(1);
                    cartItem.setId(cartItemId);
                    return cartItem;
                } else {
                    throw new SQLException("Creating user failed, no ID found");
                }
            }
        } catch (SQLException e) {
            LOGGER.info("Unable to insert cart item in database: {}", cartItem, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public CartItem update(CartItem cartItem) {
        cartItem.setVersion(cartItem.getVersion() + 1);

        var cartItemToUpdate
                = findOne(cartItem.getId()).orElseThrow(() -> new CartNotFoundException(
                        "Cart item not found by id, + " + cartItem.getId()));

        if (cartItem.getVersion() <= (cartItemToUpdate.getVersion())) {
            throw new OptimistickLockingFailureException(
                    "CartItem is already updated by another request"
            );
        }

        cartItemToUpdate.setDateLastUpdated(LocalDateTime.now());
        cartItemToUpdate.setVersion(cartItem.getVersion());
        cartItemToUpdate.setProduct(cartItem.getProduct());
        cartItemToUpdate.setQuantity(cartItem.getQuantity());
        cartItemToUpdate.setPrice(cartItem.getPrice());

        try (var connection = dataSource.getConnection();
             var pstm = connection.prepareStatement(UPDATE_CART_ITEM)){

            pstm.setInt(1, cartItemToUpdate.getQuantity());
            pstm.setBigDecimal(2, cartItemToUpdate.getPrice());
            pstm.setLong(3, cartItemToUpdate.getVersion());
            pstm.setTimestamp(4, Timestamp.valueOf(cartItemToUpdate.getDateLastUpdated()));
            pstm.setLong(5, cartItemToUpdate.getId());

            pstm.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Unable to update cart item: {}", cartItem, e);
            throw new RuntimeException("Unable to update cart item", e);
        }
        return cartItemToUpdate;
    }
    @Override
    public void remove(CartItem cartItem) {
        try (var connection = dataSource.getConnection();
             var pstm = connection.prepareStatement(DELETE_CART)){
            pstm.setLong(1, cartItem.getId());
            pstm.execute();
        } catch (SQLException e) {
            LOGGER.error("Unable to delete cartItem by id: {}", cartItem.getId(), e);
            throw new RuntimeException("Unable to delete cartItem", e);
        }
    }

    private Optional<CartItem> findOne(Long id) {
        try (var connection = dataSource.getConnection();
             var pstm = connection.prepareStatement(SELECT_CART_ITEM)){

            pstm.setLong(1, id);
            var resultSet = pstm.executeQuery();

            List<CartItem> cartItems = extractCartItems(resultSet);

            if (cartItems.size() > 0) {
                return Optional.of(cartItems.get(0));
            }
        } catch (SQLException e) {
            LOGGER.error("Unable to find cartItem by id: {}", id, e);
            throw new RuntimeException("Unable to find cartItem", e);
        }
        return Optional.empty();
    }

    private List<CartItem> extractCartItems(ResultSet resultSet)
            throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();

        while (resultSet.next()) {
            var cartItem = new CartItem();
            cartItem.setId(resultSet.getLong("id"));
            cartItem.setQuantity(resultSet.getInt("quantity"));
            cartItem.setPrice(resultSet.getBigDecimal("price"));
            cartItem.setVersion(resultSet.getLong("version"));
            cartItem.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());
            cartItem.setDateLastUpdated(resultSet.getTimestamp("date_last_updated").toLocalDateTime());

            cartItems.add(cartItem);
        }

        return cartItems;
    }
}
