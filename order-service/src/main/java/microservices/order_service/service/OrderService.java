package microservices.order_service.service;

import microservices.order_service.dto.OrderDetails;
import microservices.order_service.entity.Order;
import microservices.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    @Autowired
    OrderRepository repository;

    public String createOrder(OrderDetails orderDetails) {

//        if (orderDetails.getOrderId() == null || orderDetails.getOrderId().isEmpty()) {
//            orderDetails.setOrderId(java.util.UUID.randomUUID().toString());
//        }

        Order order = Order.builder()
//                .orderId(orderDetails.getOrderId())
                .emailId(orderDetails.getEmailId())
                .product(orderDetails.getProduct())
                .totalAmount(orderDetails.getTotalAmount())
                .build();

        order = repository.save(order);

        return order.getOrderId();
    }

    public OrderDetails getOrderDetails(String orderID) {

        Order order = repository.findById(orderID).orElseThrow(() ->
                new RuntimeException("Order not found"));

        return OrderDetails.builder()
                .emailId(order.getEmailId())
                .orderId(order.getOrderId())
                .product(order.getProduct())
                .totalAmount(order.getTotalAmount())
                .build();
    }

}