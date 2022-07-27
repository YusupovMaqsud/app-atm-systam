package uz.pdp.appatmsystam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appatmsystam.entity.Card;

public interface CardRepository extends JpaRepository<Card,Long> {
}
