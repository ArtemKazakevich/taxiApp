package by.citytaxi.application;

import by.citytaxi.application.model.GeolocationUser;
import by.citytaxi.application.model.User;
import by.citytaxi.application.model.enums.UserRoleEnum;
import by.citytaxi.application.repository.UserRepository;
import by.citytaxi.application.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

     @Autowired
     private UserRepository userRepository;

     private UserServiceImpl userService;
     private User user;
     
     @BeforeEach
     public void createNewUser() {
          userService = new UserServiceImpl();
          user = new User(
                 0,
                  UserRoleEnum.ADMIN,
                 "string",
                 "string",
                 "string@gmail.com ",
                  "string",
                  null,
                  new GeolocationUser(0.0, 0.0),
                  null
          );
          userService.addUser(user);
     }

     @Test
     public void getUser() {
          User newUser = userService.getUser(1L);
          assertEquals(newUser, user);
     }
}
