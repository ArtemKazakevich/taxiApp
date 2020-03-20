package by.citytaxi.application.model.request;

import by.citytaxi.application.model.Car;
import by.citytaxi.application.model.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarRequest {
     private UserRoleEnum userRole;
     private Car car;
}
