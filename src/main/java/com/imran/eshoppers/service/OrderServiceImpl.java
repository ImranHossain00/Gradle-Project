package com.imran.eshoppers.service;

import com.imran.eshoppers.domain.Order;
import com.imran.eshoppers.domain.ShippingAddress;
import com.imran.eshoppers.domain.User;
import com.imran.eshoppers.exception.CartItemNotFoundException;
import com.imran.eshoppers.modeldto.ShippingAddressDTO;
import com.imran.eshoppers.repository.CartRepository;
import com.imran.eshoppers.repository.OrderRepository;
import com.imran.eshoppers.repository.ShippingAddressRepository;

public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private ShippingAddressRepository shippingAddressRepository;
    private CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ShippingAddressRepository shippingAddressRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public void processOrder(ShippingAddressDTO shippingAddressDTO,
                             User currentUser)
            throws CartItemNotFoundException {

        var shippingAddress
                = convertTo(shippingAddressDTO);
        var savedShippingAddress
                = shippingAddressRepository.save(shippingAddress);

        var cart
                = cartRepository.
                findByUser(currentUser).
                orElseThrow(() ->
                        new CartItemNotFoundException(
                                "Cart Not found by cur User")
                );
        Order order = new Order();
        order.setCart(cart);
        order.setShippingAddress(savedShippingAddress);
        order.setShipped(false);
        order.setUser(currentUser);

        orderRepository.save(order);
    }

    private ShippingAddress convertTo(ShippingAddressDTO shippingAddressDTO) {
        var shippingAddress
                = new ShippingAddress();
        shippingAddress.setAddress(shippingAddressDTO.getAddress());
        shippingAddress.setAddress2(shippingAddressDTO.getAddress2());
        shippingAddress.setCountry(shippingAddressDTO.getCountry());
        shippingAddress.setState(shippingAddressDTO.getState());
        shippingAddress.setZip(shippingAddressDTO.getZip());
        shippingAddress.setMobileNumber(shippingAddressDTO.getMobileNumber());
        return shippingAddress;
    }
}
