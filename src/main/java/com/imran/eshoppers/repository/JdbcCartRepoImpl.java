package com.imran.eshoppers.repository;

import com.imran.eshoppers.domain.Cart;
import com.imran.eshoppers.domain.CartItem;
import com.imran.eshoppers.domain.User;
import com.imran.eshoppers.exception.CartNotFoundException;
import com.imran.eshoppers.exception.OptimistickLockingFailureException;
import com.imran.eshoppers.jdbc.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcCartRepoImpl implements CartRepository{

    private JdbcTemplate jdbcTemplate
            = new JdbcTemplate();
    private ProductRepository productRepository
            = new JdbcProductRepoImpl();

    private static final String INSERT_CART
            = "insert into cart (" +
             " total_price, " +
             " total_item, " +
             " version, " +
             " date_created, " +
             " date_last_updated, " +
             " user_id) values(?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_USER
            = "select c.*" +
             " from cart c" +
             "          inner join user u on (c.user_id = u.id" +
             " where u.id = ?" +
             " and (c.id not in (select cart_id from `order`))";

    private static final String FIND_BY_ID
            = "select id, " +
            " total_price, " +
            " total_item, " +
            " version, " +
            " date_created, " +
            " date_last_updated, " +
            " user_id from cart where id = ? ";

    private static final String FIND_ALL_CART_ITEMS
            = "select * from " +
             " cart_item c" +
             " where c.cart_id = ?";

    private static final String UPDATE_CART
            = "update cart " +
             " set total_price = ?, " +
             " total_item = ?, " +
             " version = ?, " +
             " date_last_updated = ? where id = ? ";
    @Override
    public Optional<Cart> findByUser(User curUser) {
        var carts
                = jdbcTemplate.queryForObject(FIND_BY_USER,
                curUser.getId(),
                resultSet -> {
                    var cart = extractCart(resultSet);
                    List<CartItem> allCartItems
                            = findAllCartItems(cart.getId());
                    cart.getCartItems().addAll(allCartItems);
                    return cart;
                });
        return carts.size() > 0 ? Optional.of(carts.get(0)) : Optional.empty();
    }

    private List<CartItem> findAllCartItems(Long id) {
        return jdbcTemplate
                .queryForObject(FIND_ALL_CART_ITEMS,
                        id,
                        resultSet -> {
                        var cartItem = new CartItem();
                        cartItem.setId(resultSet.getLong("id"));
                        cartItem.setQuantity(resultSet.getInt("quantity"));
                        cartItem.setPrice(resultSet.getBigDecimal("price"));
                        cartItem.setVersion(resultSet.getLong("version"));
                        cartItem.setDateCreated(
                                resultSet.getTimestamp(
                                        "date_created").toLocalDateTime());
                        cartItem.setDateLastUpdated(
                                resultSet.getTimestamp(
                                        "date_last_updated").toLocalDateTime());
                        var productId = resultSet.getLong("product_id");
                        productRepository.findById(productId).ifPresent(cartItem::setProduct);

                        return cartItem;
                        });
    }

    @Override
    public Cart save(Cart cart) {
        var id = jdbcTemplate.executeInsertQuery(INSERT_CART,
                cart.getTotalPrice(),
                cart.getTotalItem(),
                0L,
                cart.getDateCreated(),
                cart.getDateLastUpdated(),
                cart.getUser().getId());

        cart.setId(id);
        return cart;
    }
    @Override
    public Cart update(Cart cart) {
        cart.setVersion(cart.getVersion() + 1);
        var cartToUpdate
                = findOne(cart.getId())
                .orElseThrow(() ->
                        new CartNotFoundException(
                                "Shopping cart not found by id " + cart.getId()
                        ));
        if (cart.getVersion() <= (cartToUpdate.getVersion())) {
            throw new OptimistickLockingFailureException(
                    "Shopping cart is already updated "
                    + "by anoher request"
            );
        }

        cartToUpdate.setTotalPrice(cart.getTotalPrice());
        cartToUpdate.setTotalItem(cart.getTotalItem());
        cartToUpdate.setVersion(cart.getVersion());
        cartToUpdate.setDateLastUpdated(cart.getDateLastUpdated());
        cartToUpdate.setCartItems(cart.getCartItems());

        jdbcTemplate.updateQuery(UPDATE_CART,
                cartToUpdate.getTotalPrice(),
                cartToUpdate.getTotalItem(),
                cartToUpdate.getVersion(),
                cartToUpdate.getDateLastUpdated(),
                cartToUpdate.getId());

        return cartToUpdate;
    }

    @Override
    public Optional<Cart> findOne(Long cartId) {
        var carts
                = jdbcTemplate.
                queryForObject(
                        FIND_BY_ID,
                        cartId,
                        this::extractCart);

        return carts.size() > 0
                ? Optional.of(carts.get(0))
                : Optional.empty();
    }

    private Cart extractCart(ResultSet resultSet)
            throws SQLException {
        var cart = new Cart();
        cart.setId(resultSet.getLong("id"));
        cart.setTotalPrice(resultSet.getBigDecimal("total_price"));
        cart.setTotalItem(resultSet.getInt("total_item"));
        cart.setVersion(resultSet.getLong("version"));
        cart.setDateCreated(resultSet
                .getTimestamp("date_created")
                .toLocalDateTime());
        cart.setDateLastUpdated(resultSet
                .getTimestamp("date_last_updated")
                .toLocalDateTime());

        return cart;
    }
}
