package by.citytaxi.application.service.impl;

import by.citytaxi.application.exception.user.AuthenticationUserException;
import by.citytaxi.application.exception.user.UserNotFoundException;
import by.citytaxi.application.model.User;
import by.citytaxi.application.model.enums.UserRoleEnum;
import by.citytaxi.application.repository.UserRepository;
import by.citytaxi.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class UserServiceImpl implements UserService {
     private UserRepository userRepository;
     
     @Autowired
     public UserServiceImpl(UserRepository userRepository) {
          this.userRepository = userRepository;
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
     public void deleteUser(Long id, String token) {
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
     public void logout(String token) {
          Optional<User> byToken = userRepository.findUserByToken(token);
          if (byToken.isPresent()) {
               User user = byToken.get();
               user.setToken(null);
               userRepository.save(user);
          }
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
}
