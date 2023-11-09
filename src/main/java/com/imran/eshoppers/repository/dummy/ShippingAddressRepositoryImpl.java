package com.imran.eshoppers.repository.dummy;

import com.imran.eshoppers.domain.ShippingAddress;
import com.imran.eshoppers.repository.ShippingAddressRepository;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ShippingAddressRepositoryImpl  implements ShippingAddressRepository {

    private static final Set<ShippingAddress> SHIPPING_ADDRESSES
            = new CopyOnWriteArraySet<>();

    @Override
    public ShippingAddress save(ShippingAddress shippingAddress) {
        SHIPPING_ADDRESSES.add(shippingAddress);
        return shippingAddress;
    }

    @Override
    public Optional<ShippingAddress> findOne(long id) {
        return SHIPPING_ADDRESSES
                .stream()
                .filter(shippingAddress ->
                        shippingAddress.getId()
                                .equals(id))
                .findFirst();
    }
}
