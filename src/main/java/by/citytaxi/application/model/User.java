package by.citytaxi.application.model;

import by.citytaxi.application.model.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
     
     @Id
     @NotNull
     @PositiveOrZero
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private long id;
     
     private UserRoleEnum userRole;
     private String firstName;
     private String lastName;
     private String email;
     
     @NotBlank
     private String password;
     private String token;
     private double latitude;
     private double longitude;
}
