package eshoppers.service;

import com.imran.eshoppers.modeldto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllProductSortByName();
}
