package com.example.demo.Service;

import com.example.demo.DTO.ProductCartDTO;
import com.example.demo.Model.Product;
import com.example.demo.Model.ProductCart;
import com.example.demo.Repository.ProductCartRepository;
import com.example.demo.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final ProductCartRepository productCartRepository;
    private final ProductRepository productRepository;

    public CartService(ProductCartRepository productCartRepository, ProductRepository productRepository) {
        this.productCartRepository = productCartRepository;
        this.productRepository = productRepository;
    }

    private ProductCartDTO convertToDto(ProductCart cartItem) {
        ProductCartDTO cartItemDTO = new ProductCartDTO();
        cartItemDTO.setProductId(cartItem.getProduct().getId());
        cartItemDTO.setProductName(cartItem.getProduct().getName());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        return cartItemDTO;
    }

    public List<ProductCartDTO> getUserCart(Long userId){
        List<ProductCart> cartItems = productCartRepository.findAll();
        return cartItems.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductCartDTO addToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return null;
        }
        ProductCart cartItem = new ProductCart();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        productCartRepository.save(cartItem);
        return convertToDto(cartItem);
    }

    public void removeFromCart(Long cartItemId) {
        productCartRepository.deleteById(cartItemId);
    }

    public double getTotalPrice(Long userId) {
        List<ProductCart> cartItems = productCartRepository.findAll();
        double totalPrice = 0.0;
        for (ProductCart cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }
}
