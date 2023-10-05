package com.imran.shopping.cart.service;

import com.imran.shopping.cart.modeldto.ProductDTO;
import com.imran.shopping.cart.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {
    private final static ProductDTO APPLE_I_PAD
            = new ProductDTO(
                    "Apple iPad",
            "Apple iPad 10.2.32GB",
            BigDecimal.valueOf(369.99)
    );

    private final static ProductDTO HEADPHONE
            = new ProductDTO(
            "Headphones",
            "Jabra Elite Bluetooth",
            BigDecimal.valueOf(249.99)
    );

    private ProductRepository productRepository;
    private ProductService productService;

    @Before
    public void setUp() throws Exception {
        // productRepository is needed to create productService obj
        productRepository
                = mock(ProductRepository.class);
        productService
                = new ProductServiceImpl(productRepository);
    }

    @Test
    public void testFindAllProductSortedByName() {
        when(productRepository.findAllProduct())
                .thenReturn(
                        List.of(
                                HEADPHONE,
                                APPLE_I_PAD
                        )
                );
        var sortedByName
                = productService.findAllProductSortByName();
        Assert.assertEquals(APPLE_I_PAD.getName(),
                sortedByName.get(0).getName());
        Assert.assertEquals(HEADPHONE.getName(),
                sortedByName.get(1).getName());
    }
}
