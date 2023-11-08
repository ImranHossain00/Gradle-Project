package com.imran.eshoppers.repository;

import com.imran.eshoppers.domain.CartItem;
import com.imran.eshoppers.exception.CartNotFoundException;
import com.imran.eshoppers.exception.OptimistickLockingFailureException;
import com.imran.eshoppers.jdbc.ConnectionPool;
import com.imran.eshoppers.jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCartItemRepoImpl implements CartItemRepository{

    private JdbcTemplate jdbcTemplate
            = new JdbcTemplate();
    private ProductRepository productRepository
            = new JdbcProductRepoImpl();
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
             " date_last_updated, " +
             " cart_id " +
             " ) " +
             " values (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_CART_ITEM
            = "update cart_item " +
             " set quantity = ?, " +
             " price = ?, " +
             " version = ?, " +
             " date_last_updated = ? where id = ? " ;

    private static final String SELECT_CART_ITEM
            = "select id, " +
             " quantity, " +
             " price, " +
             " product_id, " +
             " version, " +
             " date_created, " +
             " date_last_updated, " +
             " cart_id " +
             " from cart_item where id = ?";

    private static final String DELETE_CART
            = "delete from cart_item where id = ?";
    @Override
    public CartItem save(CartItem cartItem) {
        var cartItemId
                = jdbcTemplate
                .executeInsertQuery(
                        INSERT_CART_ITEM,
                cartItem.getQuantity(),
                cartItem.getPrice(),
                cartItem.getProduct().getId(),
                0L,
                cartItem.getDateCreated(),
                cartItem.getDateLastUpdated(),
                cartItem.getCart().getId());
        cartItem.setId(cartItemId);

        return cartItem;
    }

    @Override
    public CartItem update(CartItem cartItem) {
        cartItem.setVersion(cartItem.getVersion() + 1);

        var cartItemToUpdate
                = findOne(cartItem.getId())
                .orElseThrow(
                        () -> new CartNotFoundException(
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

        jdbcTemplate.updateQuery(UPDATE_CART_ITEM,
                cartItemToUpdate.getQuantity(),
                cartItemToUpdate.getPrice(),
                cartItemToUpdate.getVersion(),
                cartItemToUpdate.getDateLastUpdated(),
                cartItemToUpdate.getId());

        return cartItemToUpdate;
    }
    @Override
    public void remove(CartItem cartItem) {
        jdbcTemplate.deleteByID(DELETE_CART, cartItem.getId());
    }

    private Optional<CartItem> findOne(Long id) {
        var cartItems
                = jdbcTemplate.queryForObject(SELECT_CART_ITEM,
                id, resultSet -> {
                    var cartItem = new CartItem();
                    cartItem.setId(resultSet.getLong("id"));
                    cartItem.setQuantity(resultSet.getInt("quantity"));
                    cartItem.setPrice(resultSet.getBigDecimal("price"));
                    cartItem.setVersion(resultSet.getLong("version"));
                    cartItem.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());
                    cartItem.setDateLastUpdated(resultSet.getTimestamp("date_last_updated").toLocalDateTime());

                    var productId
                            = resultSet.getLong("product_id");
                    productRepository.findById(productId)
                            .ifPresent(cartItem::setProduct);

                    return  cartItem;
                });
        return cartItems.size() > 0 ? Optional.of(cartItems.get(0)) : Optional.empty();
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
