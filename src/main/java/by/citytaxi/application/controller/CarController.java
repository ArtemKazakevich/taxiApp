package by.citytaxi.application.controller;

import by.citytaxi.application.exception.user.LoginUserException;
import by.citytaxi.application.exception.user.RoleUserException;
import by.citytaxi.application.model.Car;
import by.citytaxi.application.model.request.CarRequest;
import by.citytaxi.application.service.impl.CarServiceImpl;
import by.citytaxi.application.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/car")
@Validated
@Slf4j
public class CarController {
     private CarServiceImpl carService;
     private UserServiceImpl userService;
     
     public CarController(CarServiceImpl carService, UserServiceImpl userService) {
          this.carService = carService;
          this.userService = userService;
     }
     
     @GetMapping(path = "/{id}")
     public ResponseEntity<Car> getCar (@PathVariable @Min(value = 0) Long id,
                                         @RequestBody CarRequest carRequest,
                                          @RequestHeader(name = "apiKey") String apiKey) {
          if (!userService.isLoggedIn(apiKey)) throw new LoginUserException();
          if (!userService.isAdmin(carRequest.getUserRole())) throw new RoleUserException();
          
          log.info("The car impression was completed successfully");
          return new ResponseEntity<>(carService.getCar(id), HttpStatus.OK);
     }
     
     @PostMapping
     public ResponseEntity<String> createCar(@RequestBody CarRequest carRequest,
                                             @RequestHeader(name = "apiKey") String apiKey) {
          if (!userService.isLoggedIn(apiKey)) throw new LoginUserException();
          if (!userService.isAdmin(carRequest.getUserRole())) throw new RoleUserException();
          
          carService.addCar(carRequest.getCar());
          log.info("The car is created");
          return new ResponseEntity<>("The car is created", HttpStatus.OK);
     }
     
     @PutMapping(path = "/{id}")
     public ResponseEntity<String> updateCar(@PathVariable @Min(value = 0) Long id,
                                              @RequestBody CarRequest carRequest,
                                              @RequestHeader(name = "apiKey") String apiKey) {
          if (!userService.isLoggedIn(apiKey)) throw new LoginUserException();
          if (!userService.isAdmin(carRequest.getUserRole())) throw new RoleUserException();
          
          carService.updateCar(id, carRequest.getCar());
          log.info("Car with id={} was updated", id);
          return new ResponseEntity<>("The car is successfully updated", HttpStatus.OK);
     }
     
     @DeleteMapping(path = "/{id}")
     public ResponseEntity<String> deleteCar(@PathVariable @Min(value = 0) Long id,
                                              @RequestBody CarRequest carRequest,
                                              @RequestHeader(name = "apiKey") String apiKey) {
          if (!userService.isLoggedIn(apiKey)) throw new LoginUserException();
          if (!userService.isAdmin(carRequest.getUserRole())) throw new RoleUserException();
          
          carService.deleteCar(id);
          log.info("Car with id={} was removed", id);
          return new ResponseEntity<>("The car was successfully deleted", HttpStatus.OK);
     }
     
     @PostMapping(path = "/sortName")
     public ResponseEntity<List<Car>> sortNameCar(@RequestBody CarRequest carRequest,
                                                  @RequestHeader(name = "apiKey") String apiKey) {
          if (!userService.isLoggedIn(apiKey)) throw new LoginUserException();
          if (!userService.isAdmin(carRequest.getUserRole())) throw new RoleUserException();
          
          log.info("Sorting by name is completed");
          return new ResponseEntity<>(carService.sortNameCar(), HttpStatus.OK);
     }
     
     @PostMapping(path = "/sortNumberArea")
     public ResponseEntity<List<Car>> sortNumberAreaCar(@RequestBody CarRequest carRequest,
                                                  @RequestHeader(name = "apiKey") String apiKey) {
          if (!userService.isLoggedIn(apiKey)) throw new LoginUserException();
          if (!userService.isAdmin(carRequest.getUserRole())) throw new RoleUserException();
          
          log.info("Sorting by area number is complete");
          return new ResponseEntity<>(carService.sortNumberAreaCar(), HttpStatus.OK);
     }
}
