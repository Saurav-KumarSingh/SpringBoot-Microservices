package microservices.user_service.Dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserInformation {

    private String name;

    private  String email;

    private String password;

    private List<OrderDto> orders = new ArrayList<>();

}
