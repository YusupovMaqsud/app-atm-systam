package uz.pdp.appatmsystam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appatmsystam.entity.Bank;
@RepositoryRestResource(path = "bank")
public interface BankRepository extends JpaRepository<Bank, Long> {
}
