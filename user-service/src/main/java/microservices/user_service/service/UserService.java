package microservices.user_service.service;

import microservices.user_service.Dto.UserInformation;
import microservices.user_service.entity.User;
import microservices.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
                    return newUser;
                });
    }
}