package com.bazlur.eshoppers.service;

import com.bazlur.eshoppers.modeldto.ProductDTO;
import com.bazlur.eshoppers.repository.ProductRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public List<ProductDTO> findAllProductSortByName() {
        return productRepository.findAllProduct()
                .stream()
                .sorted(Comparator.comparing(ProductDTO::getName))
                .collect(Collectors.toList());
    }
}
