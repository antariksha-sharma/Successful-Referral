package in.simplifymoney.successfulreferral.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {

    private String userId;

    private String name;

    private String email;

    private boolean profileCompleted;

    private int totalReferrals;

    private List<String> referredUsers;

    private String referralCode;

    private String referredBy;
}
