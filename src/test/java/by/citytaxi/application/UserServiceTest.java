package by.citytaxi.application;

import by.citytaxi.application.model.User;
import by.citytaxi.application.model.enums.UserRoleEnum;
import by.citytaxi.application.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
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
                  "string",
                  0.0,
                  0.0
          );
          userService.addUser(user);
     }
}
