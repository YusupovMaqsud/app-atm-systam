package uz.pdp.appatmsystam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appatmsystam.entity.template.AbstractEntity;
import uz.pdp.appatmsystam.enums.CardTypeEnum;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bankomat extends AbstractEntity {

    @Enumerated(value = EnumType.STRING)
    private CardTypeEnum cardType;

    @ManyToOne
    private Bank bank;

    @Column(nullable = false)
    private Long maxWithdrawalAmount; //yechiladigan max pul miqdori

    @Column(nullable = false)
    private int percent; //foizi

    @Column(nullable = false)
    private int notCardpercent; //foizi

    private Double minAmount=100000d;
    private Double maxAmount=1000000000d;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private double balance;

    @Column(columnDefinition = "text")
    private String address;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Money> moneys;

    private boolean active = true;

}
