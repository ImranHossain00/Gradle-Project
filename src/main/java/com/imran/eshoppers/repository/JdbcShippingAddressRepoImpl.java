package com.imran.eshoppers.repository;

import com.imran.eshoppers.domain.ShippingAddress;
import com.imran.eshoppers.jdbc.JdbcTemplate;

import java.util.Optional;

public class JdbcShippingAddressRepoImpl implements ShippingAddressRepository{
    private JdbcTemplate jdbcTemplate
            = new JdbcTemplate();

    private static final String INSERT_SHIPPING_ADDRESS
            = "insert into shipping_address ( " +
             " address, " +
             " address2, " +
             " state, " +
             " zip, " +
             " country, " +
             " version, " +
             " mobile_number, " +
             " date_created, " +
             " date_last_updated) " +
             " values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String FIND_SHIPPING_ADDRESS_BY_ID
            = "select id, " +
             " address, " +
             " address2, " +
             " state, " +
             " zip, " +
             " country, " +
             " version, " +
             " date_created, " +
             " date_last_updated " +
             " from shipping_address where id = ? ";
    @Override
    public ShippingAddress save(ShippingAddress shippingAddress) {
        var id = jdbcTemplate.executeInsertQuery(
                INSERT_SHIPPING_ADDRESS,
                shippingAddress.getAddress(),
                shippingAddress.getAddress2(),
                shippingAddress.getState(),
                shippingAddress.getZip(),
                shippingAddress.getCountry(),
                0L,
                shippingAddress.getMobileNumber(),
                shippingAddress.getDateCreated(),
                shippingAddress.getDateLastUpdated()
        );

        shippingAddress.setId(id);
        return shippingAddress;
    }

    @Override
    public Optional<ShippingAddress> findOne(long id) {
        var shippingAddresses = jdbcTemplate.queryForObject(
                FIND_SHIPPING_ADDRESS_BY_ID,
                id, resultSet -> {
                    var shippingAddress = new ShippingAddress();
                    shippingAddress.setId(resultSet.getLong("id"));
                    shippingAddress.setAddress(resultSet.getString("address"));
                    shippingAddress.setAddress2(resultSet.getString("address2"));
                    shippingAddress.setState(resultSet.getString("state"));
                    shippingAddress.setZip(resultSet.getString("zip"));
                    shippingAddress.setCountry(resultSet.getString("country"));
                    shippingAddress.setVersion(resultSet.getLong("version"));
                    shippingAddress.setMobileNumber(resultSet.getString("mobile_number"));
                    shippingAddress.setDateCreated(resultSet
                            .getTimestamp("date_created")
                            .toLocalDateTime());
                    shippingAddress.setDateLastUpdated(resultSet
                            .getTimestamp("date_last_updated")
                            .toLocalDateTime());
                    return shippingAddress;
                }
        );
        return shippingAddresses.size() > 0
                ? Optional.of(shippingAddresses.get(0))
                : Optional.empty();
    }
}
