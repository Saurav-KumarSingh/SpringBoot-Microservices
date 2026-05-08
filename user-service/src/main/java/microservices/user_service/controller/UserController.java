package microservices.user_service.controller;

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

    @GetMapping("/{email}")
    public Optional<UserInformation> getUser(@PathVariable String email){

        return userService.getUser(email);
    }

    @GetMapping
    public List<UserInformation> getAllUser(){
        return userService.getAllUser();
    }
}