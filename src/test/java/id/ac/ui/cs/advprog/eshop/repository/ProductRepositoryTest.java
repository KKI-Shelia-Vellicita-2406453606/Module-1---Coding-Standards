package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    private static final String PRODUCT_ID = "eb558e9f-1c39-460e-8860-71af6af63bd6";
    private static final String PRODUCT_NAME_BAMBANG = "Sampo Cap Bambang";

    @InjectMocks
    ProductRepository productRepository;

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId(PRODUCT_ID);
        product.setProductName(PRODUCT_NAME_BAMBANG);
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
        product1.setProductId(PRODUCT_ID);
        product1.setProductName(PRODUCT_NAME_BAMBANG);
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9896");
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
    void testEditProductPositive() {
        Product product = new Product();
        product.setProductId(PRODUCT_ID);
        product.setProductName(PRODUCT_NAME_BAMBANG);
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(PRODUCT_ID);
        updatedProduct.setProductName("Sampo Cap Bango");
        updatedProduct.setProductQuantity(200);
        productRepository.update(updatedProduct);

        Product savedProduct = productRepository.findById(updatedProduct.getProductId());
        assertEquals(updatedProduct.getProductName(), savedProduct.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testEditProductNegative() {
        Product product = new Product();
        product.setProductId("existing-id");
        product.setProductName("Original");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");
        nonExistentProduct.setProductName("Fake");
        nonExistentProduct.setProductQuantity(0);

        Product result = productRepository.update(nonExistentProduct);
        assertNull(result);
    }

    @Test
    void testDeleteProductPositive() {
        Product product = new Product();
        product.setProductId(PRODUCT_ID);
        productRepository.create(product);

        productRepository.delete(PRODUCT_ID);
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteProductNegative() {
        Product product = new Product();
        product.setProductId("stay-here");
        productRepository.create(product);

        productRepository.delete("wrong-id");

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
    }

    @Test
    void testCreateGeneratesIdWhenNull() {
        Product product = new Product();
        product.setProductName("NoIdProduct");
        product.setProductQuantity(5);

        Product created = productRepository.create(product);
        assertNotNull(created.getProductId());

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product saved = productIterator.next();
        assertEquals(created.getProductId(), saved.getProductId());
    }

    @Test
    void testFindByIdNotFoundReturnsNull() {
        // ensure repository is empty
        Iterator<Product> it = productRepository.findAll();
        while (it.hasNext()) {
            productRepository.delete(it.next().getProductId());
        }

        Product result = productRepository.findById("non-existent-id-xyz");
        assertNull(result);
    }
}