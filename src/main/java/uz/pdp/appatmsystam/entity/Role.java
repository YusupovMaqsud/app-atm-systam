package uz.pdp.appatmsystam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appatmsystam.entity.template.AbstractEntity;
import uz.pdp.appatmsystam.enums.PermissionEnum;
import uz.pdp.appatmsystam.enums.RoleEnum;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends AbstractEntity {

    @Column(nullable = false, unique = true)//bo'sh bo'lmasligi kerak,ADMIN,USER takrorlanmasligi k-k
    private String name;

    @Enumerated(EnumType.STRING)
    private RoleEnum roleName;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<PermissionEnum> permissionEnum;

}
