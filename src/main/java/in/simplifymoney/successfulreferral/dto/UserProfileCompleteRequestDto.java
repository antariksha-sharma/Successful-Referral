package in.simplifymoney.successfulreferral.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserProfileCompleteRequestDto {

    @NotNull(message = "Phone number cannot be null")
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    @NotNull(message = "Address Line 1 cannot be null")
    @NotBlank(message = "Address Line 1 cannot be blank")
    private String addressLine1;

    @NotNull(message = "Address Line 2 cannot be null")
    @NotBlank(message = "Address Line 2 cannot be blank")
    private String addressLine2;

    @NotNull(message = "City cannot be null")
    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotNull(message = "State cannot be null")
    @NotBlank(message = "State cannot be blank")
    private String state;

    @NotNull(message = "Country cannot be null")
    @NotBlank(message = "Country cannot be blank")
    private String country;

    @NotNull(message = "Zip Code cannot be null")
    @NotBlank(message = "Zip Code cannot be blank")
    private String zipCode;

    @NotNull(message = "Gender cannot be null")
    @NotBlank(message = "Gender cannot be blank")
    private String gender;
}
