package eshoppers.service;

import com.imran.eshoppers.domain.User;
import com.imran.eshoppers.exception.CartItemNotFoundException;
import com.imran.eshoppers.modeldto.ShippingAddressDTO;

public interface OrderService {
    void processOrder(ShippingAddressDTO shippingAddress, User currentUser) throws CartItemNotFoundException;
}
