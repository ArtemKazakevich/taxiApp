package by.citytaxi.application.service.impl;

import by.citytaxi.application.exception.car.CarNotFoundException;
import by.citytaxi.application.model.Car;
import by.citytaxi.application.repository.CarRepository;
import by.citytaxi.application.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarServiceImpl implements CarService {
     private CarRepository carRepository;
     
     @Autowired
     public CarServiceImpl(CarRepository carRepository) {
          this.carRepository = carRepository;
     }
     
     @Override
     public void addCar(Car car) {
          carRepository.save(car);
     }
     
     @Override
     public Car getCar(Long id) {
          return carRepository.findById(id).orElseThrow(CarNotFoundException::new);
     }
     
     @Override
     public void updateCar(Long id, Car car) {
          Optional<Car> byId = carRepository.findById(id);
          if (byId.isPresent()) {
               carRepository.save(car);
          } else throw new CarNotFoundException();
     }
     
     @Override
     public void deleteCar(Long id) {
          Optional<Car> byId = carRepository.findById(id);
          if (byId.isPresent()) {
               carRepository.deleteById(id);
          } else throw new CarNotFoundException();
     }
     
     @Override
     public List<Car> sortNameCar() {
          return carRepository.findAllNameCars();
     }
     
     @Override
     public List<Car> sortNumberAreaCar() {
          return carRepository.findAllNumberAreaCars();
     }
}
