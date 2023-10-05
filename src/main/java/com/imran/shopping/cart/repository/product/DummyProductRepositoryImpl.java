package com.imran.shopping.cart.repository.product;

import com.imran.shopping.cart.dto.product.ProductDTO;

import java.math.BigDecimal;
import java.util.List;

public class DummyProductRepositoryImpl
        implements ProductRepository {
    @Override
    public List<ProductDTO> findAllProduct() {
        return List.of(
                new ProductDTO(
                        "Headphones",
                        "Jabra Elite Bluetooth",
                        BigDecimal.valueOf(249.99)
                ),
                new ProductDTO(
                        "Apple iPad",
                        "Apple iPad 10.2.32GB",
                        BigDecimal.valueOf(369.99)
                ),
                new ProductDTO(
                        "Amazon Echo Dot",
                        "Amazon Echo Dot (3rd Gen) with Alexa - Charoal",
                        BigDecimal.valueOf(34.99)
                ),
                new ProductDTO(
                        "Microsoft Surface",
                        "Microsoft Surface 7 12.3\" 128GB Windows 10 Tablet with 10th Gen intel processor",
                        BigDecimal.valueOf(1000.99)
                )
        );
    }
}
