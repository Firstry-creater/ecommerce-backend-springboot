
package com.example.ecommercebackend.service;

import com.example.ecommercebackend.entity.*;
import com.example.ecommercebackend.repository.CartRepository;
import com.example.ecommercebackend.repository.ProductRepository;
import com.example.ecommercebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Cart addToCart(Long userId,
                          Long productId,
                          Integer quantity) {

        User user = userRepository.findById(userId)
                .orElseThrow();

        Product product = productRepository.findById(productId)
                .orElseThrow();

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {

                    Cart newCart = Cart.builder()
                            .user(user)
                            .build();

                    return cartRepository.save(newCart);
                });

        CartItem item = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();

        cart.getItems().add(item);

        return cartRepository.save(cart);
    }

    public Cart getCart(Long userId) {

        return cartRepository.findByUserId(userId)
                .orElseThrow();
    }

    public void clearCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow();

        cart.getItems().clear();

        cartRepository.save(cart);
    }
}