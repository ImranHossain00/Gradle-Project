package com.techCottage.repositories;

import com.techCottage.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;

public class DummyProductRepoImpl implements ProductRepo{

    // Dummu list of products.
    final List<ProductDTO> PRODUCTS
            = List.of(
                    new ProductDTO(
                            "Mac air",
                            "AirBook purple 16GB 1000TB",
                            BigDecimal.valueOf(223.99)
                    ),
                    new ProductDTO(
                            "Apple iPad",
                            "Apple iPad 10.2 32GB",
                            BigDecimal.valueOf(359.99)
                    ),
                    new ProductDTO(
                            "Headphones",
                            "Jabra Elit Bluetooth Headphones",
                            BigDecimal.valueOf(223.99)
                    ),
                    new ProductDTO(
                            "Asus AirBook",
                            "Asus Air Book 16GB 500GB",
                            BigDecimal.valueOf(333.99)
                    )

            );

    @Override
    public List<ProductDTO> findAll() {
        return PRODUCTS;
    }
}
