package com.imran.eshoppers.repository.dummy;

import com.imran.eshoppers.domain.Order;
import com.imran.eshoppers.domain.User;
import com.imran.eshoppers.repository.OrderRepository;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class OrderRepositoryImpl implements OrderRepository {

    private static final Set<Order> ORDERS
            = new CopyOnWriteArraySet<>();

    @Override
    public Order save(Order order) {
        ORDERS.add(order);
        return order;
    }

    @Override
    public Set<Order> findOrderByUser(User curUser) {

//        return ORDERS
//                .stream()
//                .filter(
//                        order -> Objects.equals(
//                                order.getUser().equals(curUser))
//                )
//                .findFirst();
        return ORDERS;
    }
}
