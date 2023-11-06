package eshoppers.repository;

import com.imran.eshoppers.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAllProduct();

    Optional<Product> findById(Long id);
}
