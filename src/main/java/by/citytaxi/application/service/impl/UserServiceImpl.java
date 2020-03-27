package by.citytaxi.application.service.impl;

import by.citytaxi.application.exception.car.CarNotFoundException;
import by.citytaxi.application.exception.car.CarStatusException;
import by.citytaxi.application.exception.user.AuthenticationUserException;
import by.citytaxi.application.exception.user.UserNotFoundException;
import by.citytaxi.application.model.Car;
import by.citytaxi.application.model.GeolocationUser;
import by.citytaxi.application.model.User;
import by.citytaxi.application.model.enums.UserRoleEnum;
import by.citytaxi.application.repository.CarRepository;
import by.citytaxi.application.repository.UserRepository;
import by.citytaxi.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class UserServiceImpl implements UserService {
     private UserRepository userRepository;
     private CarRepository carRepository;

     @Autowired
     public UserServiceImpl(UserRepository userRepository, CarRepository carRepository) {
          this.userRepository = userRepository;
          this.carRepository = carRepository;
     }
     
     public UserServiceImpl() {
     }
     
     @Override
     public void addUser(User user) {
          userRepository.save(user);
     }
     
     @Override
     public User getUser(Long id) {
          return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
     }
     
     @Override
     public void updateUser(Long id, User user) {
          Optional<User> byId = userRepository.findById(id);
          if (byId.isPresent()) {
               userRepository.save(user);
          } else throw new UserNotFoundException();
     }
     
     @Override
     public void deleteUser(Long id) {
          Optional<User> byId = userRepository.findById(id);
          if (byId.isPresent()) {
               userRepository.deleteById(id);
          } else throw new UserNotFoundException();
     }
     
     @Override
     public String authentication(User user) {
          Optional<User> byEmail = userRepository.findUserByEmail(user.getEmail());
          if (byEmail.isPresent()) {
               if (byEmail.get().getToken() != null) throw new AuthenticationUserException("User has already logged in");
               if (byEmail.get().getPassword().equals(user.getPassword())) {
                    SecureRandom random = new SecureRandom();
                    byte bytes[] = new byte[20];
                    random.nextBytes(bytes);
                    String token = bytes.toString();
                    
                    User newUser = byEmail.get();
                    newUser.setToken(token);
                    userRepository.save(newUser);
                    return token;
               }
          }
          return null;
     }
     
     @Override
     public boolean isLoggedIn(String token) {
          Optional<User> byToken = userRepository.findUserByToken(token);
          return byToken.isPresent();
     }
     
     @Override
     public boolean isAdmin(UserRoleEnum userRoleEnum) {
          return userRoleEnum.equals(UserRoleEnum.ADMIN);
     }
     
     @Override
     public List<User> sortLastNameUser() {
          return userRepository.findAllLastNameUser();
     }

     //     here extra loops are only used for the application test
     @Override
     public void userCallCar(Car car, User user, String token) {
          List<Car> listCarNumberArea = carRepository.findAllByNumberAreaAndStatus(car.getNumberArea(), car.isStatus());
          String numberCar = null;
          int count = 0;

          for (Car value : listCarNumberArea) {
               Random random = new Random();
               int timeCar = random.nextInt(11) + 5;
               value.setTime(timeCar);
          }

          int carMinTime = listCarNumberArea.get(0).getTime();

          for (Car value : listCarNumberArea) {
               if (value.getTime() < carMinTime) {
                    carMinTime = value.getTime();
               }
          }

          for (Car value : listCarNumberArea) {
               if (!value.isStatus() && value.getTime() == carMinTime) {
                    value.setStatus(true);
                    carRepository.save(value);
                    numberCar = value.getNumber();
                    count++;
                    break;
               }
          }

          if (count == 0) throw new CarStatusException();

          Optional<User> byToken = userRepository.findUserByToken(token);
          if (byToken.isPresent()) {
               User newUser = byToken.get();
               newUser.setGeolocationUser(user.getGeolocationUser());
               newUser.setNumberCar(numberCar);
               userRepository.save(newUser);
          } else throw new UserNotFoundException();
     }

     @Override
     public void userArrived(String token) {
          Optional<User> byToken = userRepository.findUserByToken(token);
          String numberCar;

          if (byToken.isPresent()) {
               User user = byToken.get();
               numberCar = user.getNumberCar();
               user.setNumberCar(null);
               user.setGeolocationUser(new GeolocationUser(0.0, 0.0));
               userRepository.save(user);
          } else throw new UserNotFoundException();

          Optional<Car> byNumberCar = carRepository.findUserByNumber(numberCar);
          if (byNumberCar.isPresent()) {
               Car car = byNumberCar.get();
               car.setStatus(false);
               car.setTime(0);
               carRepository.save(car);
          } else throw new CarNotFoundException();
     }
}
