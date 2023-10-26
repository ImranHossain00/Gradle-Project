package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.modeldto.ProductDTO;

import java.util.List;

public interface ProductRepository {
    List<ProductDTO> findAllProduct();
}
