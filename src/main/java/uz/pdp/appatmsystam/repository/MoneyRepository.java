package uz.pdp.appatmsystam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appatmsystam.entity.Money;

import java.util.Optional;
@RepositoryRestResource(path = "money")
public interface MoneyRepository extends JpaRepository<Money, Long> {
}
