package in.simplifymoney.successfulreferral.mapper;

import in.simplifymoney.successfulreferral.dto.UserRequestDto;
import in.simplifymoney.successfulreferral.dto.UserResponseDto;
import in.simplifymoney.successfulreferral.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto userToUserResponseDto(User user);

    User userRequestDtoToUser(UserRequestDto userRequestDto);
}
