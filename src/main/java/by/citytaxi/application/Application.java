package by.citytaxi.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application {

     public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
          log.info("The program is running");
     }

}
