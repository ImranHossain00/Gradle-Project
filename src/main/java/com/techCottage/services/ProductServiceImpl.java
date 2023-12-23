package com.techCottage.services;

import com.techCottage.dto.ProductDTO;
import com.techCottage.repositories.ProductRepo;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class ProductServiceImpl implements ProductService{

    private ProductRepo repository;

    public ProductServiceImpl(ProductRepo repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductDTO> findAllProductSortedByName() {
        return repository
                .findAll()
                .stream()
                .sorted(comparing(ProductDTO::getName))
                .collect(Collectors.toList());
    }
}
