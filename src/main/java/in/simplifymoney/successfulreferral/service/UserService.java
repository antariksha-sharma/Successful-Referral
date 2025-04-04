package in.simplifymoney.successfulreferral.service;

import in.simplifymoney.successfulreferral.dto.UserProfileCompleteRequestDto;
import in.simplifymoney.successfulreferral.dto.UserRequestDto;
import in.simplifymoney.successfulreferral.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserResponseDto registerUser(UserRequestDto userRequestDto);

    UserResponseDto getUserByIdOrEmail(String idOrEmail);

    UserResponseDto completeUserProfile(String idOrEmail, UserProfileCompleteRequestDto userProfileCompleteRequestDto);

    List<UserResponseDto> getReferrals(String userIdOrEmail);

    public byte[] generateReferralReportCSV(HttpServletResponse response) throws IOException;
}
