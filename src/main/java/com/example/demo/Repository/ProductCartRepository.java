package com.example.demo.Repository;

import com.example.demo.Model.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCartRepository extends JpaRepository<ProductCart,Long> {
}
