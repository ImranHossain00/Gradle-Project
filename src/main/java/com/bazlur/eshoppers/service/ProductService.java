package com.bazlur.eshoppers.service;

import com.bazlur.eshoppers.modeldto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllProductSortByName();
}
