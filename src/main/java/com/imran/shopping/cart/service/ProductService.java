package com.imran.shopping.cart.service;

import com.imran.shopping.cart.modeldto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllProductSortByName();
}
