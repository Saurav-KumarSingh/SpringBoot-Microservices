package microservices.order_service.controller;

import microservices.order_service.dto.OrderDetails;
import microservices.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    public String createOrder(@RequestBody OrderDetails orderDetails) {
        return orderService.createOrder(orderDetails);
    }
    @GetMapping("/details/{orderID}")
    public OrderDetails fetchOrder(@PathVariable String orderID) {
        return orderService.getOrderDetails(orderID);
    }

    @GetMapping("/user")
    public List<OrderDetails> getUserOrders(@RequestParam String emailId){
        return orderService.getUserOrders(emailId);
    }

    @GetMapping
    public List<OrderDetails> getAllOrders(){
        return orderService.getAllOrders();
    }
}