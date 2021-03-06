package by.citytaxi.application.controller;

import by.citytaxi.application.exception.user.AuthenticationUserException;
import by.citytaxi.application.exception.user.LoginUserException;
import by.citytaxi.application.exception.user.RoleUserException;
import by.citytaxi.application.exception.user.UserNotFoundException;
import by.citytaxi.application.model.Car;
import by.citytaxi.application.model.User;
import by.citytaxi.application.model.request.CarRequest;
import by.citytaxi.application.model.request.UserRequest;
import by.citytaxi.application.repository.UserRepository;
import by.citytaxi.application.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
@Validated
@Slf4j
public class UserController {
     private UserServiceImpl userService;
     private UserRepository userRepository;

     public UserController(UserServiceImpl userService, UserRepository userRepository) {
          this.userService = userService;
          this.userRepository = userRepository;
     }
     
     @GetMapping(path = "/{id}")
     public ResponseEntity<User> getUser (@PathVariable @Min(value = 0) Long id,
                                          @RequestHeader(name = "apiKey") String apiKey) {
          if (!userService.isLoggedIn(apiKey)) throw new LoginUserException();
          
          log.info("The user impression was completed successfully");
          return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
     }
     
     @PostMapping
     public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
          userService.addUser(user);
          log.info("The user is created");
          return new ResponseEntity<>("The user is created", HttpStatus.OK);
     }
     
     @PutMapping(path = "/{id}")
     public ResponseEntity<String> updateUser(@PathVariable @Min(value = 0) Long id,
                                              @RequestBody UserRequest userRequest,
                                              @RequestHeader(name = "apiKey") String apiKey) {
          if (!userService.isLoggedIn(apiKey)) throw new LoginUserException();
          if (!userService.isAdmin(userRequest.getUserRole())) throw new RoleUserException();
          
          userService.updateUser(id, userRequest.getUser());
          log.info("User with id={} was updated", id);
          return new ResponseEntity<>("The user is successfully updated", HttpStatus.OK);
     }
     
     @DeleteMapping(path = "/{id}")
     public ResponseEntity<String> deleteUser(@PathVariable @Min(value = 0) Long id,
                                              @RequestBody UserRequest userRequest,
                                              @RequestHeader(name = "apiKey") String apiKey) {
          if (!userService.isLoggedIn(apiKey)) throw new LoginUserException();
          if (!userService.isAdmin(userRequest.getUserRole())) throw new RoleUserException();
          
          userService.deleteUser(id);
          log.info("User with id={} was removed", id);
          return new ResponseEntity<>("The user was successfully deleted", HttpStatus.OK);
     }
     
     @PostMapping(path = "/login")
     public ResponseEntity<String> loginUser(@RequestBody UserRequest userRequest) {
          String token = userService.authentication(userRequest.getUser());
          if (token == null) throw new AuthenticationUserException("Invalid email/password supplied");
          
          log.info("Login completed");
          return new ResponseEntity<>(token, HttpStatus.OK);
     }

     @PostMapping(path = "/logout")
     public ResponseEntity<String> logoutUser(@RequestHeader(name = "apiKey") String apiKey) {
          if (!userService.isLoggedIn(apiKey)) throw new LoginUserException();

          Optional<User> byToken = userRepository.findUserByToken(apiKey);
          if (byToken.isPresent()) {
               User user = byToken.get();
               user.setToken(null);
               userRepository.save(user);
          } else throw new UserNotFoundException();

          log.info("Exit successfully completed");
          return new ResponseEntity<>("Exit successfully completed", HttpStatus.OK);
     }
     
     @PostMapping(path = "/sortLastName")
     public ResponseEntity<List<User>> sortNameCar(@RequestBody UserRequest userRequest,
                                                  @RequestHeader(name = "apiKey") String apiKey) {
          if (!userService.isLoggedIn(apiKey)) throw new LoginUserException();
          if (!userService.isAdmin(userRequest.getUserRole())) throw new RoleUserException();
          
          log.info("Sorting by name is completed");
          return new ResponseEntity<>(userService.sortLastNameUser(), HttpStatus.OK);
     }

     @PostMapping(path = "/callCar")
     public ResponseEntity<String> userCallCar(@RequestBody CarRequest carRequest,
                                               @RequestHeader(name = "apiKey") String apiKey) {
          if (!userService.isLoggedIn(apiKey)) throw new LoginUserException();

          userService.userCallCar(carRequest.getCar(), carRequest.getUser(), apiKey);
          log.info("Call accepted");
          return new ResponseEntity<>("Call accepted", HttpStatus.OK);
     }

     @PostMapping(path = "/userArrived")
     public ResponseEntity<String> userArrived(@RequestHeader(name = "apiKey") String apiKey) {
          if (!userService.isLoggedIn(apiKey)) throw new LoginUserException();

          userService.userArrived(apiKey);
          log.info("The person was successfully delivered");
          return new ResponseEntity<>("The person was successfully delivered", HttpStatus.OK);
     }
}
