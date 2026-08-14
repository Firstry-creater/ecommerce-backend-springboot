

package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.entity.Cart;
import com.example.ecommercebackend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public Cart addToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam Integer quantity
    ) {

        return cartService.addToCart(
                userId,
                productId,
                quantity
        );
    }

    @GetMapping("/{userId}")
    public Cart getCart(
            @PathVariable Long userId
    ) {

        return cartService.getCart(userId);
    }

    @DeleteMapping("/{userId}")
    public String clearCart(
            @PathVariable Long userId
    ) {

        cartService.clearCart(userId);

        return "Cart Cleared Successfully";
    }
}