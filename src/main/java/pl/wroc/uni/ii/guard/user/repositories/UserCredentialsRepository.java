package pl.wroc.uni.ii.guard.user.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.wroc.uni.ii.guard.user.models.entities.UserCredentials;

import java.util.Optional;


public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    Optional<UserCredentials> findByEmail(String email);
}