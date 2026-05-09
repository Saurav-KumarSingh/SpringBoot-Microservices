package microservices.user_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String emailId;
    private String orderId;
    private String product;
    private BigDecimal totalAmount;
}