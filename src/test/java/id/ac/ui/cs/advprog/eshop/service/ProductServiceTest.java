package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.create(any(Product.class))).thenReturn(product);
        
        Product createdProduct = productService.create(product);
        
        verify(productRepository, times(1)).create(product);
        assertEquals(product.getProductId(), createdProduct.getProductId());
        assertEquals(product.getProductName(), createdProduct.getProductName());
        assertEquals(product.getProductQuantity(), createdProduct.getProductQuantity());
    }

    @Test
    void testFindAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        
        Iterator<Product> productIterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(productIterator);
        
        List<Product> foundProducts = productService.findAll();
        
        verify(productRepository, times(1)).findAll();
        assertEquals(1, foundProducts.size());
        assertEquals(product.getProductId(), foundProducts.get(0).getProductId());
    }

    @Test
    void testEditProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(200);

        when(productRepository.edit(eq(product.getProductId()), any(Product.class)))
            .thenReturn(updatedProduct);

        Product result = productService.edit(product.getProductId(), updatedProduct);

        verify(productRepository, times(1)).edit(product.getProductId(), updatedProduct);
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).delete(product.getProductId());
        
        productService.delete(product.getProductId());
        
        verify(productRepository, times(1)).delete(product.getProductId());
    }
}
