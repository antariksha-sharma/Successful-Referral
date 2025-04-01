package in.simplifymoney.successfulreferral.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.simplifymoney.successfulreferral.enums.Gender;
import in.simplifymoney.successfulreferral.enums.ReferredStatus;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {

    private String userId;

    private String name;

    private String email;

    private String password;

    private boolean profileCompleted;

    private String referralCode;

    private String referredBy;

    private ReferredStatus referredStatus;

    private String phoneNumber;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String country;

    private String zipCode;

    private Gender gender;
}
