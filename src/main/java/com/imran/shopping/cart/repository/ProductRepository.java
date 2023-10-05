package com.imran.shopping.cart.repository;

import com.imran.shopping.cart.modeldto.ProductDTO;

import java.util.List;

public interface ProductRepository {
    List<ProductDTO> findAllProduct();
}
