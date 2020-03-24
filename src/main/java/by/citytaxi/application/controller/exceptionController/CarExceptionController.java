package by.citytaxi.application.controller.exceptionController;

import by.citytaxi.application.exception.car.CarNotFoundException;
import by.citytaxi.application.exception.car.CarStatusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class CarExceptionController extends ResponseEntityExceptionHandler {
     
     @Override
     protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
          log.warn("Car not valid");
          return new ResponseEntity<>("Car not valid", HttpStatus.BAD_REQUEST);
     }
     
     @ExceptionHandler(CarNotFoundException.class)
     public ResponseEntity<String> exCarNotFound() {
          log.warn("Car not found");
          return new ResponseEntity<>("Car not found", HttpStatus.NOT_FOUND);
     }
     
     @ExceptionHandler(CarStatusException.class)
     public ResponseEntity<String> exCarStatus() {
          log.warn("There are no available cars");
          return new ResponseEntity<>("Sorry, all the cars are busy at the moment", HttpStatus.NOT_FOUND);
     }
}
