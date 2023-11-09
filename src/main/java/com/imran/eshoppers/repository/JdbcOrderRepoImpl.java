package com.imran.eshoppers.repository;

import com.imran.eshoppers.domain.Order;
import com.imran.eshoppers.domain.User;
import com.imran.eshoppers.jdbc.JdbcTemplate;

import java.util.HashSet;
import java.util.Set;

public class JdbcOrderRepoImpl implements OrderRepository{
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    private CartRepository cartRepository
            = new JdbcCartRepoImpl();
    private ShippingAddressRepository shippingAddressRepository
            = new JdbcShippingAddressRepoImpl();

    private static final String FIND_ORDER_BY_USER
            = "select id, " +
             " shipping_address_id, " +
             " cart_id, " +
             " version, " +
             " date_created, " +
             " date_last_updated, " +
             " shipping_date, " +
             " shipped, " +
             " user_id from `order` where user_id = ?";

    private static final String INSERT_ORDER
            = "insert into `order` (" +
             " shipping_address_id, " +
             " cart_id, " +
             " version, " +
             " shipped, " +
             " user_id, " +
             " date_created, " +
             " date_last_updated) " +
             " values (?, ?, ?, ?, ?, ?, ?)";
    @Override
    public Order save(Order order) {
        var id = jdbcTemplate.executeInsertQuery(
                INSERT_ORDER,
                order.getShippingAddress().getId(),
                order.getCart().getId(),
                0L,
                order.isShipped(),
                order.getUser().getId(),
                order.getDateCreated(),
                order.getDateLastUpdated()
        );
        order.setId(id);
        return order;
    }

    @Override
    public Set<Order> findOrderByUser(User curUser) {
        var orders = jdbcTemplate.queryForObject(
                FIND_ORDER_BY_USER,
                curUser.getId(),
                resultSet -> {
                    var order = new Order();
                    order.setId(resultSet.getLong("id"));
                    order.setVersion(resultSet.getLong("version"));
                    order.setDateCreated(resultSet
                            .getTimestamp("date_created")
                            .toLocalDateTime());
                    order.setDateLastUpdated(resultSet
                            .getTimestamp("date_last_updated")
                            .toLocalDateTime());
                    order.setShipped(resultSet.getBoolean("shipped"));
                    order.setShippingDate(resultSet
                            .getTimestamp("shipping_date") != null
                            ? resultSet.getTimestamp("shipping-date").toLocalDateTime()
                            : null);

                    cartRepository.findOne(
                            resultSet.getLong("cart_id"))
                            .ifPresent(order::setCart);

                    shippingAddressRepository.findOne(
                            resultSet.getLong("shipping_address_id"))
                            .ifPresent(order::setShippingAddress);
                    order.setUser(curUser);
                    return order;
                }
        );
        return new HashSet<>(orders);
    }
}
