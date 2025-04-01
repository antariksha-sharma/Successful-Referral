package in.simplifymoney.successfulreferral.service;

import in.simplifymoney.successfulreferral.dto.UserRequestDto;
import in.simplifymoney.successfulreferral.dto.UserResponseDto;

public interface UserService {
    UserResponseDto saveUser(UserRequestDto userRequestDto);

    UserResponseDto getUserByIdOrEmail(String idOrEmail);
}
