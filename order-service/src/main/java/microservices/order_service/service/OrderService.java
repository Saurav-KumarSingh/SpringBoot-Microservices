package microservices.order_service.service;

import microservices.order_service.dto.OrderDetails;
import microservices.order_service.entity.Order;
import microservices.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

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

        order = orderRepository.save(order);

        return order.getOrderId();
    }

    public OrderDetails getOrderDetails(String orderID) {

        Order order = orderRepository.findById(orderID).orElseThrow(() ->
                new RuntimeException("Order not found"));

        return OrderDetails.builder()
                .emailId(order.getEmailId())
                .orderId(order.getOrderId())
                .product(order.getProduct())
                .totalAmount(order.getTotalAmount())
                .build();
    }

    public List<OrderDetails> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(order -> {
                    OrderDetails newOrder=new OrderDetails();
                    newOrder.setOrderId(order.getOrderId());
                    newOrder.setEmailId(order.getEmailId());
                    newOrder.setProduct(order.getProduct());
                    newOrder.setTotalAmount(order.getTotalAmount());

                    return newOrder;
                }).collect(Collectors.toList());
    }

    public List<OrderDetails> getUserOrders(String emailId) {

        return orderRepository.findByEmailId(emailId)
                .stream()
                .map(order -> {
                    OrderDetails newOrder=new OrderDetails();
                    newOrder.setOrderId(order.getOrderId());
                    newOrder.setEmailId(order.getEmailId());
                    newOrder.setProduct(order.getProduct());
                    newOrder.setTotalAmount(order.getTotalAmount());

                    return newOrder;
                }).collect(Collectors.toList());
    }
}