package uz.pdp.appatmsystam.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyDto {

    @NotNull
    private Integer banknoteId;

    @NotNull
    private Integer amount;

    @NotNull
    private Integer bankomatId;

    private Integer accountTypeId;
}
