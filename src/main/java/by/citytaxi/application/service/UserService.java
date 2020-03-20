package by.citytaxi.application.service;

import by.citytaxi.application.model.User;
import by.citytaxi.application.model.enums.UserRoleEnum;

public interface UserService {
     
     void addUser(User user);
     
     User getUser(Long id);
     
     void updateUser(Long id, User user);
     
     void deleteUser(Long id);
     
     String authentication(User user);
     
     void logout(String token);
     
     boolean isLoggedIn(String token);
     
     boolean isAdmin(UserRoleEnum userRoleEnum);
}
