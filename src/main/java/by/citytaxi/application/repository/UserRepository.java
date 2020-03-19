package by.citytaxi.application.repository;

import by.citytaxi.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findUserByToken(String token);
     Optional<User> findUserByEmail(String email);
}
