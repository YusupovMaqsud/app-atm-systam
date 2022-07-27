package uz.pdp.appatmsystam.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appatmsystam.entity.Money;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankomatDto {
    @NotNull
    private String address;

    @NotNull
    private Integer bankId;

    @NotNull
    private String cardType;

    @NotNull
    private List<Money> moneys;


}
