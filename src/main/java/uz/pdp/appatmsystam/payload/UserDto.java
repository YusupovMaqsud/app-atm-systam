package uz.pdp.appatmsystam.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appatmsystam.enums.RoleEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Email(message = "Email talab qilinadi")
    @NotNull
    private String email;

    @NotNull
    private RoleEnum role;

    @NotNull
    @NotBlank
    private String username;

    private Integer bank;
}
