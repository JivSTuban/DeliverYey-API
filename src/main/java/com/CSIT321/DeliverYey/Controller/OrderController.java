package com.CSIT321.DeliverYey.Controller;

import com.CSIT321.DeliverYey.Entity.OrderEntity;
import com.CSIT321.DeliverYey.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/insertOrder")
    public OrderEntity insertOrder(@RequestBody OrderEntity order) {
        return orderService.insertOrder(order);
    }

    @GetMapping("/getAllOrder")
    public List<OrderEntity> getAllOrder() {
        return orderService.getAllOrder();
    }

    @PutMapping("/updateOrder")
    public OrderEntity updateOrder(@RequestParam int orderId, @RequestBody OrderEntity newOrderDetails) {
        return orderService.updateOrder(orderId, newOrderDetails);
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public String deleteOrder(@PathVariable int orderId) {
        return orderService.deleteOrder(orderId);
    }
}
