package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository productRepository; // Used to verify data directly

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        service.create(product);

        List<Product> productList = service.findAll();
        assertFalse(productList.isEmpty());
        Product savedProduct = productList.get(0);

        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId("find-id-123");
        product.setProductName("Find Me");
        product.setProductQuantity(5);

        service.create(product);

        Product found = service.findById("find-id-123");
        assertNotNull(found);
        assertEquals(product.getProductId(), found.getProductId());
        assertEquals(product.getProductName(), found.getProductName());
    }

    @Test
    void testUpdateAndDelete() {
        Product product = new Product();
        product.setProductId("upd-id-123");
        product.setProductName("Before Update");
        product.setProductQuantity(1);

        service.create(product);

        Product updated = new Product();
        updated.setProductId("upd-id-123");
        updated.setProductName("After Update");
        updated.setProductQuantity(99);

        service.update(updated);

        Product saved = productRepository.findById("upd-id-123");
        assertNotNull(saved);
        assertEquals("After Update", saved.getProductName());
        assertEquals(99, saved.getProductQuantity());

        service.delete("upd-id-123");
        List<Product> productList = service.findAll();
        // after deletion the list should not contain the deleted item
        boolean present = productList.stream().anyMatch(p -> "upd-id-123".equals(p.getProductId()));
        assertFalse(present);
    }
}