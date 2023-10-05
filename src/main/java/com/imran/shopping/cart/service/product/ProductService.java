package com.imran.shopping.cart.service.product;

import com.imran.shopping.cart.dto.product.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllProductSortByName();
}
