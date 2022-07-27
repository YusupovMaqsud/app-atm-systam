package uz.pdp.appatmsystam.entity;

import lombok.*;
import uz.pdp.appatmsystam.entity.template.AbstractEntity;
import uz.pdp.appatmsystam.enums.CardTypeEnum;

import javax.persistence.*;
import java.sql.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card extends AbstractEntity {

    @Column(length = 16, nullable = false, unique = true) //login
    private String number;

    @ManyToOne(cascade = CascadeType.ALL)
    private Bank bank;

    @Column(length = 3, nullable = false,unique = true)
    private String cvv;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private Date expiredDate; //amal qilish muddati

    @Column(length = 4, nullable = false,unique = true)
    private String password;

    @Enumerated(value = EnumType.STRING)
    private CardTypeEnum cardType;

    private boolean active=true;

}
