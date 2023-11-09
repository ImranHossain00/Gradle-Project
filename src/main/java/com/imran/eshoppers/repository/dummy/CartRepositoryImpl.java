package com.imran.eshoppers.repository.dummy;

import com.imran.eshoppers.domain.Cart;
import com.imran.eshoppers.domain.Order;
import com.imran.eshoppers.domain.User;
import com.imran.eshoppers.repository.CartRepository;
import com.imran.eshoppers.repository.JdbcOrderRepoImpl;
import com.imran.eshoppers.repository.OrderRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CartRepositoryImpl implements CartRepository {
    // we used static because Map must be same for all instances or objects
    // that will be created by users.
    // here Map used for optimizing the time complexity
    // for ref see page no.169 in we book
    private static final Map<User, Set<Cart>> CARTS
            = new ConcurrentHashMap<>(); // for thread safe here we used ConcurrentHashMap.
                                         // see page no.167 in web book

    private final OrderRepository orderRepository
            = new JdbcOrderRepoImpl();
    @Override
    public Optional<Cart> findByUser(User curUser) {
        var usersCart = getCart(curUser);
        if (usersCart.isPresent()) {
            var cart = usersCart.get();
            var orders
                    = orderRepository.findOrderByUser(curUser);

            if (isOrderAlreadyPlacedWith(orders, cart)) {
                return Optional.empty();
            } else {
                return Optional.of(cart);
            }
        }
        return Optional.empty();
    }

    private boolean isOrderAlreadyPlacedWith(Set<Order> orders,
                                             Cart cart) {
        return orders.
                stream().
                noneMatch(
                        order -> order.getCart().equals(cart)
                );
    }

    private Optional<Cart> getCart(User curUser) {
        var carts = CARTS.get(curUser);
        if (carts != null && !carts.isEmpty()) {
            Cart cart = (Cart) carts.toArray()[carts.size() - 1];
            return Optional.of(cart);
        }
        return Optional.empty();
    }
    @Override
    public Cart save(Cart cart) {
        CARTS.computeIfPresent(cart.getUser(),
                ((user, carts) -> {
            carts.add(cart);
            return carts;
        }));

        CARTS.computeIfAbsent(cart.getUser(),
                user -> {
            var carts = new LinkedHashSet<Cart>();
            carts.add(cart);
            return carts;
        });
        return cart;
    }

    @Override
    public Cart update(Cart cart) {
        CARTS.computeIfPresent(cart.getUser(), ((user, carts) -> {
            Cart[] objects = carts.toArray(Cart[]::new);
            objects[objects.length - 1] = cart;

            return new LinkedHashSet<>(Arrays.asList(objects));
        }));
        return cart;
    }

    @Override
    public Optional<Cart> findOne(Long cartId) {
        return Optional.empty();
    }
}
