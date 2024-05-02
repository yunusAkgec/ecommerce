package com.example.demo.Controller;

import com.example.demo.DTO.ProductCartDTO;
import com.example.demo.Service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ProductCartDTO>> getUserCart(@PathVariable Long userId){
        List<ProductCartDTO> cart = cartService.getUserCart(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{productId")
    public ResponseEntity<ProductCartDTO> addToCart(@PathVariable Long productId, @RequestParam int quantity){
        ProductCartDTO cartItem = cartService.addToCart(productId,quantity);
        if (cartItem==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartItemId){
        cartService.removeFromCart(cartItemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total/{userId}")
    public ResponseEntity<Double> getTotalPrice(@PathVariable Long userId){
        double totalPrice = cartService.getTotalPrice(userId);
        return ResponseEntity.ok(totalPrice);
    }

}
