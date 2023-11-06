package com.imran.eshoppers.repository;

import com.imran.eshoppers.domain.Order;
import com.imran.eshoppers.domain.User;

import java.util.Set;

public interface OrderRepository {
    Order save(Order order);

    Set<Order> findOrderByUser(User curUser);
}
