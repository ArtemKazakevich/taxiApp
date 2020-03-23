package by.citytaxi.application.service;

import by.citytaxi.application.model.Car;

import java.util.List;

public interface CarService {
     
     void addCar(Car car);
     
     Car getCar(Long id);
     
     void updateCar(Long id, Car car);
     
     void deleteCar(Long id);
     
     List<Car> sortNameCar();
     
     List<Car> sortNumberAreaCar();
}
