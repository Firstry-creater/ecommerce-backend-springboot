
package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.entity.Order;
import com.example.ecommercebackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{userId}")
    public Order placeOrder(
            @PathVariable Long userId
    ) {

        return orderService.placeOrder(userId);
    }

    @GetMapping("/{userId}")
    public List<Order> getOrders(
            @PathVariable Long userId
    ) {

        return orderService.getOrders(userId);
    }
}