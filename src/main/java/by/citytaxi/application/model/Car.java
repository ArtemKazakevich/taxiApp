package by.citytaxi.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
     
     @Id
     @NotNull
     @PositiveOrZero
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private long id;
     
     private String name;
     private String number;
     private int numberArea;
}
