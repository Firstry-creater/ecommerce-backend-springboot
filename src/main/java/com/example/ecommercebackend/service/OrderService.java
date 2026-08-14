

package com.example.ecommercebackend.service;

import com.example.ecommercebackend.entity.*;
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

    public Order placeOrder(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Cart is empty"));

        Order order = new Order();

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());

            BigDecimal price = cartItem.getProduct().getPrice();

            orderItem.setPrice(price);

            total = total.add(
                    price.multiply(
                            BigDecimal.valueOf(cartItem.getQuantity())
                    )
            );

            order.getOrderItems().add(orderItem);
        }

        order.setTotalAmount(total);

        cart.getItems().clear();

        cartRepository.save(cart);

        return orderRepository.save(order);
    }

    public java.util.List<Order> getOrders(Long userId) {

        return orderRepository.findByUserId(userId);
    }

}