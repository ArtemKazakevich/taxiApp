package by.citytaxi.application.controller.exceptionController;

import by.citytaxi.application.exception.user.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class UserExceptionController extends ResponseEntityExceptionHandler {
     
     @Override
     protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
          log.warn("User not valid");
          return new ResponseEntity<>("User not valid", HttpStatus.BAD_REQUEST);
     }
     
     @ExceptionHandler(value = {ConstraintViolationException.class})
     protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {
          return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
     }
     
     @ExceptionHandler(UserNotFoundException.class)
     public ResponseEntity<String> exUserNotFound() {
          log.warn("User not found");
          return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
     }
     
     @ExceptionHandler(UserBadRequestException.class)
     public ResponseEntity exUserBadRequest() {
          log.warn("Bad Request");
          return new ResponseEntity("Bad Request", HttpStatus.BAD_REQUEST);
     }
     
     @ExceptionHandler(AuthenticationUserException.class)
     public ResponseEntity<String> exUserAuth(AuthenticationUserException e) {
          log.warn(e.getMessage());
          return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
     }
     
     @ExceptionHandler(LoginUserException.class)
     public ResponseEntity<String> exUserLogin() {
          log.warn("User is not logged in");
          return new ResponseEntity<>("User is not logged in", HttpStatus.BAD_REQUEST);
     }
     
     @ExceptionHandler(DeleteUserException.class)
     public ResponseEntity<String> exUserDelete() {
          log.warn("Cannot delete user");
          return new ResponseEntity<>("Cannot delete user", HttpStatus.BAD_REQUEST);
     }
     
     @ExceptionHandler(RoleUserException.class)
     public ResponseEntity<String> exUserRole() {
          log.warn("You don't have enough rights");
          return new ResponseEntity<>("You don't have enough rights", HttpStatus.BAD_REQUEST);
     }
}
