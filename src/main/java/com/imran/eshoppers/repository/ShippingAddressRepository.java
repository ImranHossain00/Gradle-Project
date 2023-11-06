package com.imran.eshoppers.repository;

import com.imran.eshoppers.domain.ShippingAddress;

public interface ShippingAddressRepository {
    ShippingAddress save(ShippingAddress shippingAddress);
}
