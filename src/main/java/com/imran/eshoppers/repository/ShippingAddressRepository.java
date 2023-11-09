package com.imran.eshoppers.repository;

import com.imran.eshoppers.domain.ShippingAddress;

import java.util.Optional;

public interface ShippingAddressRepository {
    ShippingAddress save(ShippingAddress shippingAddress);
    Optional<ShippingAddress> findOne(long id);
}
