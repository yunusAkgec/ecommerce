package com.example.demo.Service;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private ProductDTO convertToDto(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        return productDTO;
    }
    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        return product;
    }

    public List<ProductDTO> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id){
        Product product = productRepository.findById(id).orElse(null);
        return product != null ? convertToDto(product) : null;
    }

    public ProductDTO createProduct(ProductDTO productDTO){
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO){
        Product product = productRepository.findById(id).orElse(null);
        if (product ==null){
            return null;
        }
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        Product updatedProduct = productRepository.save(product);
        return convertToDto(updatedProduct);
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).orElse(null);
        if (product!=null){
            productRepository.delete(product);
        }
    }
}
