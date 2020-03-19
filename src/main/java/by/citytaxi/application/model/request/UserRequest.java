package by.citytaxi.application.model.request;

import by.citytaxi.application.model.User;
import by.citytaxi.application.model.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
     private String token;
     private UserRoleEnum userRole;
     private User user;
}
