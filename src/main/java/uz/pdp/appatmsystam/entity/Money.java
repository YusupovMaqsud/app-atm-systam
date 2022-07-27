package uz.pdp.appatmsystam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appatmsystam.entity.template.AbstractEntity;
import uz.pdp.appatmsystam.enums.MoneyTypeEnum;

import javax.persistence.*;
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Money extends AbstractEntity {

    @Column(nullable = false)
    private Long quantity;//miqdori

    @Enumerated(EnumType.STRING)
    private MoneyTypeEnum moneyTypeEnum;

    private Double summa;

    private boolean active = true;
}
