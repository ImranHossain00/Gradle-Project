package com.imran.shopping.cart.repository.product;

import com.imran.shopping.cart.dto.product.ProductDTO;

import java.util.List;

public interface ProductRepository {
    List<ProductDTO> findAllProduct();
}
