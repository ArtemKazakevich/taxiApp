package by.citytaxi.application.repository;

import by.citytaxi.application.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
     
     @Query(value = "SELECT * FROM car ORDER BY name", nativeQuery = true)
     List<Car> findAllNameCars();
     
     @Query(value = "SELECT * FROM car ORDER BY number_area", nativeQuery = true)
     List<Car> findAllNumberAreaCars();
     
     List<Car> findAllByNumberAreaAndStatus(int numberArea, boolean status);

     Optional<Car> findUserByNumber(String number);
}
