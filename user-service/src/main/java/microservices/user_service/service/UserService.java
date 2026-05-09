package microservices.user_service.service;

import microservices.user_service.Dto.OrderDto;
import microservices.user_service.Dto.UserInformation;
import microservices.user_service.entity.User;
import microservices.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public String createUser(UserInformation userInformation) {

        User user = new User();

        if (userRepository.existsByEmail(userInformation.getEmail())) {
            return "Email already exists!";
        }
        user.setName(userInformation.getName());
        user.setEmail(userInformation.getEmail());
        user.setPassword(userInformation.getPassword());

        userRepository.save(user);

        return "User created successfully";
    }

    public Optional<UserInformation> getUser(String email) {

        return userRepository.findByEmail(email)
                .map(user -> {
                    UserInformation newUser = new UserInformation();
                    newUser.setName(user.getName());
                    newUser.setEmail(user.getEmail());
                    newUser.setPassword(user.getPassword());

                    String url = "http://localhost:8080/api/order/user?emailId=" + email;

                    OrderDto[] response = restTemplate.getForObject(url, OrderDto[].class);

                    List<OrderDto> orders = response != null
                            ? Arrays.asList(response)
                            : new ArrayList<>();

                    newUser.setOrders(orders);
                    return newUser;
                });
    }

    public List<UserInformation> getAllUser() {

        return userRepository.findAll()
                .stream()
                .map(user -> {

                    UserInformation newUser = new UserInformation();
                    newUser.setName(user.getName());
                    newUser.setEmail(user.getEmail());
                    newUser.setPassword(user.getPassword());

                    // 🔗 Call order-service
                    String url = "http://localhost:8080/api/order/user?emailId=" + user.getEmail();

                    OrderDto[] response = restTemplate.getForObject(url, OrderDto[].class);

                    List<OrderDto> orders = response != null
                            ? Arrays.asList(response)
                            : new ArrayList<>();

                    newUser.setOrders(orders);

                    return newUser;
                })
                .toList();
    }
}