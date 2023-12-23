package com.techCottage.repositories;

import com.techCottage.dto.ProductDTO;

import java.util.List;

public interface ProductRepo {
    List<ProductDTO> findAll();
}
