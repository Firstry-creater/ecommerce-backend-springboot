

package com.example.ecommercebackend.service;

import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import com.example.ecommercebackend.entity.*;
//import com.example.ecommercebackend.entity.Product;
import com.example.ecommercebackend.repository.CartRepository;
import com.example.ecommercebackend.repository.OrderRepository;
import com.example.ecommercebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order placeOrder(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Cart is empty"));

        if (cart.getItems().isEmpty()) {
            throw new BadRequestException("Cannot place order: cart is empty");
        }

        Order order = new Order();

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {

            Product product = cartItem.getProduct();

            if (product.getStock() < cartItem.getQuantity()) {
                throw new BadRequestException(
                        "Insufficient stock for product: " + product.getName()
                );
            }

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());

            total = total.add(
                    product.getPrice().multiply(
                            BigDecimal.valueOf(cartItem.getQuantity())
                    )
            );

            product.setStock(
                    product.getStock() - cartItem.getQuantity()
            );

            productRepository.save(product);

            order.getOrderItems().add(orderItem);
        }

        order.setTotalAmount(total);

        cart.getItems().clear();

        cartRepository.save(cart);

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public java.util.List<Order> getOrders(Long userId) {

        return orderRepository.findByUserId(userId);
    }

}