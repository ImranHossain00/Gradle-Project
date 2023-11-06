package eshoppers.repository;

import com.imran.eshoppers.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl
        implements ProductRepository{
    private static final List<Product> ALL_PRODUCTS = new ArrayList<>();

//    private static final List<Product> ALL_PRODUCTS
//            = List.of(
//            new Product(
//                    1L,
//                    "Headphones",
//                    "Jabra Elite Bluetooth",
//                    BigDecimal.valueOf(249.99)
//            ),
//            new Product(
//                    2L,
//                    "Apple iPad",
//                    "Apple iPad 10.2.32GB",
//                    BigDecimal.valueOf(369.99)
//            ),
//            new Product(
//                    3L,
//                    "Amazon Echo Dot",
//                    "Amazon Echo Dot (3rd Gen) with Alexa - Charoal",
//                    BigDecimal.valueOf(34.99)
//            ),
//            new Product(
//                    4L,
//                    "Microsoft Surface",
//                    "Microsoft Surface 7 12.3\" 128GB Windows 10 Tablet with 10th Gen intel processor",
//                    BigDecimal.valueOf(1000.99)
//            )
//    );
    @Override
    public List<Product> findAllProduct() {
        return ALL_PRODUCTS;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return findAllProduct()
                .stream()
                .filter(product ->
                        product.getId().equals(id))
                .findFirst();
    }
}
