package eshoppers.service;

import com.imran.eshoppers.domain.Product;
import com.imran.eshoppers.modeldto.ProductDTO;
import com.imran.eshoppers.repository.ProductRepository;

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
                .map(this::convertToDTO)
                .sorted(Comparator.comparing(ProductDTO::getName))
                .collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
