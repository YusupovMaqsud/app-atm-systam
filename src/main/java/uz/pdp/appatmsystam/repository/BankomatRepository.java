package uz.pdp.appatmsystam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appatmsystam.entity.Bank;
import uz.pdp.appatmsystam.entity.Bankomat;

import java.util.List;

public interface BankomatRepository extends JpaRepository<Bankomat, Long> {
    List<Bankomat> findAllByActiveTrue();
}
