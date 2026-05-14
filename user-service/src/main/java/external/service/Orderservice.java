package external.service;

import microservices.user_service.Dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "APIGATEWAY")
public interface Orderservice {
    @GetMapping("/api/order/user")
    List<OrderDto> getOrder(@RequestParam String emailId);
}
