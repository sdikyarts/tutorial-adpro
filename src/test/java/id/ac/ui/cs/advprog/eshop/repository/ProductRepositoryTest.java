package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindById_existingProduct() {
        Product product = new Product();
        product.setProductId("id1");
        product.setProductName("Product 1");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product found = productRepository.findById("id1");
        assertNotNull(found);
        assertEquals("id1", found.getProductId());
    }

    @Test
    void testFindById_nonExistingProduct() {
        Product found = productRepository.findById("nonexistent");
        assertNull(found);
    }

    @Test
    void testFindById_onEmptyRepository() {
        Product found = productRepository.findById("anyid");
        assertNull(found);
    }

    @Test
    void testFindById_multipleProducts_nonExistingId() {
        Product product1 = new Product();
        product1.setProductId("idA");
        product1.setProductName("A");
        product1.setProductQuantity(1);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("idB");
        product2.setProductName("B");
        product2.setProductQuantity(2);
        productRepository.create(product2);

        Product found = productRepository.findById("idC"); // not present
        assertNull(found);
    }

    @Test
    void testEdit_existingProduct() {
        Product product = new Product();
        product.setProductId("id2");
        product.setProductName("Product 2");
        product.setProductQuantity(20);
        productRepository.create(product);

        Product updated = new Product();
        updated.setProductName("Updated Name");
        updated.setProductQuantity(99);

        Product result = productRepository.edit("id2", updated);
        assertNotNull(result);
        assertEquals("id2", result.getProductId());
        assertEquals("Updated Name", result.getProductName());
        assertEquals(99, result.getProductQuantity());
    }

    @Test
    void testEdit_nonExistingProduct() {
        Product updated = new Product();
        updated.setProductName("Doesn't Matter");
        updated.setProductQuantity(1);

        Product result = productRepository.edit("nonexistent", updated);
        assertNull(result);
    }

    @Test
    void testEdit_onEmptyRepository() {
        Product updated = new Product();
        updated.setProductName("Doesn't Matter");
        updated.setProductQuantity(1);

        Product result = productRepository.edit("anyid", updated);
        assertNull(result);
    }

    @Test
    void testEdit_multipleProducts_nonExistingId() {
        Product product1 = new Product();
        product1.setProductId("idA");
        product1.setProductName("A");
        product1.setProductQuantity(1);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("idB");
        product2.setProductName("B");
        product2.setProductQuantity(2);
        productRepository.create(product2);

        Product updated = new Product();
        updated.setProductName("Updated");
        updated.setProductQuantity(99);

        Product result = productRepository.edit("idC", updated); // not present
        assertNull(result);
    }

    @Test
    void testEdit_multipleProducts_editSecond() {
        Product product1 = new Product();
        product1.setProductId("idA");
        product1.setProductName("A");
        product1.setProductQuantity(1);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("idB");
        product2.setProductName("B");
        product2.setProductQuantity(2);
        productRepository.create(product2);

        Product updated = new Product();
        updated.setProductName("Updated B");
        updated.setProductQuantity(22);

        Product result = productRepository.edit("idB", updated);
        assertNotNull(result);
        assertEquals("idB", result.getProductId());
        assertEquals("Updated B", result.getProductName());
        assertEquals(22, result.getProductQuantity());
    }

    @Test
    void testDelete_existingProduct() {
        Product product = new Product();
        product.setProductId("id3");
        product.setProductName("Product 3");
        product.setProductQuantity(30);
        productRepository.create(product);

        productRepository.delete("id3");
        assertNull(productRepository.findById("id3"));
    }

    @Test
    void testDelete_nonExistingProduct() {
        productRepository.delete("nonexistent");
    }
}