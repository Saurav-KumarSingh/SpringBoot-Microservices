package microservices.user_service.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import microservices.user_service.Dto.UserInformation;
import microservices.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public String createUser(@RequestBody UserInformation userInformation){
        return userService.createUser(userInformation);
    }

    @CircuitBreaker(name = "orderCircuitBreaker", fallbackMethod = "orderFallBack")
    @GetMapping("/{email}")
    public Optional<UserInformation> getUser(@PathVariable String email){

        return userService.getUser(email);
    }


    @CircuitBreaker(name = "orderCircuitBreaker", fallbackMethod = "getAllUserFallback")
    @GetMapping
    public List<UserInformation> getAllUser(){
        return userService.getAllUser();
    }

    //    creating fall back for circuit breaker

    public Optional<UserInformation> orderFallBack(String email,Exception ex){
        System.out.println("Fallback triggered because: " + ex.getMessage());

        UserInformation user = new UserInformation();
        user.setEmail(email);
        user.setName("Service Unavailable");

        return Optional.of(user);
    }

    public List<UserInformation> getAllUserFallback(Exception ex) {
        System.out.println("Fallback triggered: " + ex.getMessage());

        UserInformation user = new UserInformation();
        user.setName("Service Unavailable");
        user.setEmail("N/A");

        return List.of(user);
    }
}