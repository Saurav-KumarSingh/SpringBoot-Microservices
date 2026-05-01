package microservices.user_service.Dto;

import lombok.Data;

@Data
public class UserInformation {

    private String name;

    private  String email;

    private String password;
}
