package in.simplifymoney.successfulreferral.service.impl;

import in.simplifymoney.successfulreferral.dto.UserRequestDto;
import in.simplifymoney.successfulreferral.dto.UserResponseDto;
import in.simplifymoney.successfulreferral.mapper.UserMapper;
import in.simplifymoney.successfulreferral.model.User;
import in.simplifymoney.successfulreferral.repository.UserRepository;
import in.simplifymoney.successfulreferral.service.UserService;
import in.simplifymoney.successfulreferral.util.IdAndReferralCodeGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {

        User user = userMapper.userRequestDtoToUser(userRequestDto);

        user.setUserId(IdAndReferralCodeGenerator.generateId());
        user.setReferralCode(IdAndReferralCodeGenerator.generateReferralCode());
        user.setProfileCompleted(true); // Assuming profile is completed, can be set to false if profile is not completed

        if(user.getReferredBy() != null && user.isProfileCompleted()) {
            User userWhoReferred = userRepository.findByReferralCode(user.getReferredBy()).orElseThrow(() -> new RuntimeException("User not found"));
            userWhoReferred.setTotalReferrals(userWhoReferred.getTotalReferrals() + 1);
            List<String> referredUsers = userWhoReferred.getReferredUsers();
            referredUsers.add(user.getUserId());
            userWhoReferred.setReferredUsers(referredUsers);
            userRepository.save(userWhoReferred);
        }

        User savedUser = userRepository.save(user);

        return userMapper.userToUserResponseDto(savedUser);
    }

    @Override
    public UserResponseDto getUserByIdOrEmail(String idOrEmail) {
        return userRepository.findByUserIdOrEmail(idOrEmail, idOrEmail)
                .map(userMapper::userToUserResponseDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
