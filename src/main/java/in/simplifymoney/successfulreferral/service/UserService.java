package in.simplifymoney.successfulreferral.service;

import in.simplifymoney.successfulreferral.dto.UserProfileCompleteRequestDto;
import in.simplifymoney.successfulreferral.dto.UserRequestDto;
import in.simplifymoney.successfulreferral.dto.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser(UserRequestDto userRequestDto);

    UserResponseDto getUserByIdOrEmail(String idOrEmail);

    UserResponseDto completeUserProfile(String idOrEmail, UserProfileCompleteRequestDto userProfileCompleteRequestDto);
}
