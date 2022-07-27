package uz.pdp.appatmsystam.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import uz.pdp.appatmsystam.entity.template.AbstractEntity;
import uz.pdp.appatmsystam.enums.CardTypeEnum;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bank extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text",nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private CardTypeEnum card;
}
