package uz.pdp.appatmsystam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appatmsystam.entity.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndEmailCode(String email, Integer emailCode);

}
