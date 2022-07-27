package uz.pdp.appatmsystam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appatmsystam.entity.Role;
import uz.pdp.appatmsystam.enums.RoleEnum;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleEnum(RoleEnum roleEnum);

    Optional<Role> findByRoleName(RoleEnum roleEnum);
    Set<Role> findAllByIdIn(Collection<Integer> id);
}
