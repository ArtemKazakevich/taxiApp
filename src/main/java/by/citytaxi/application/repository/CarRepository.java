package by.citytaxi.application.repository;

import by.citytaxi.application.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
