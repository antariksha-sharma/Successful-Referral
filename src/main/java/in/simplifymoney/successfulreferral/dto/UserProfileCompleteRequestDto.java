package in.simplifymoney.successfulreferral.dto;

import lombok.Data;

@Data
public class UserProfileCompleteRequestDto {

    private String phoneNumber;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String country;

    private String zipCode;

    private String gender;
}
