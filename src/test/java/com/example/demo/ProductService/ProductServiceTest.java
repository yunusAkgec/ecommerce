package com.example.demo.ProductService;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    @Test
    public void testGetAllProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setDescription("Description 1");
        product1.setPrice(10.0);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setDescription("Description 2");
        product2.setPrice(20.0);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<ProductDTO> products = productService.getAllProducts();

        assertEquals(2, products.size());
        assertEquals(product1.getName(), products.get(0).getName());
        assertEquals(product1.getDescription(), products.get(0).getDescription());
        assertEquals(product1.getPrice(), products.get(0).getPrice());
        assertEquals(product2.getName(), products.get(1).getName());
        assertEquals(product2.getDescription(), products.get(1).getDescription());
        assertEquals(product2.getPrice(), products.get(1).getPrice());

    }

    @Test
    public void testGetProductById() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Product 1");
        product.setDescription("Description 1");
        product.setPrice(10.0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductDTO productDTO = productService.getProductById(productId);

        assertNotNull(productDTO);
        assertEquals(productId, productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getDescription(), productDTO.getDescription());
        assertEquals(product.getPrice(), productDTO.getPrice());
    }

    @Test
    public void testCreateProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Product 1");
        productDTO.setDescription("Description 1");
        productDTO.setPrice(10.0);

        Product product = new Product();
        product.setId(1L);
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO savedProductDTO = productService.createProduct(productDTO);

        assertNotNull(savedProductDTO);
        assertEquals(product.getId(), savedProductDTO.getId());
        assertEquals(product.getName(), savedProductDTO.getName());
        assertEquals(product.getDescription(), savedProductDTO.getDescription());
        assertEquals(product.getPrice(), savedProductDTO.getPrice());
    }

    @Test
    public void testUpdateProduct() {
        Long productId = 1L;
        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setName("Updated Product");
        updatedProductDTO.setDescription("Updated Description");
        updatedProductDTO.setPrice(20.0);

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Product");
        existingProduct.setDescription("Description");
        existingProduct.setPrice(10.0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductDTO updatedDTO = productService.updateProduct(productId, updatedProductDTO);

        assertNotNull(updatedDTO);
        assertEquals(productId, updatedDTO.getId());
        assertEquals(updatedProductDTO.getName(), updatedDTO.getName());
        assertEquals(updatedProductDTO.getDescription(), updatedDTO.getDescription());
        assertEquals(updatedProductDTO.getPrice(), updatedDTO.getPrice());
    }

    @Test
    public void testDeleteProduct() {
        Long productId = 1L;

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Product");
        existingProduct.setDescription("Description");
        existingProduct.setPrice(10.0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).delete(existingProduct);
    }
}
