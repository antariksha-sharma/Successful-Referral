package in.simplifymoney.successfulreferral.service.impl;

import in.simplifymoney.successfulreferral.dto.UserProfileCompleteRequestDto;
import in.simplifymoney.successfulreferral.dto.UserRequestDto;
import in.simplifymoney.successfulreferral.dto.UserResponseDto;
import in.simplifymoney.successfulreferral.enums.Gender;
import in.simplifymoney.successfulreferral.enums.ReferredStatus;
import in.simplifymoney.successfulreferral.exception.DuplicateEntryException;
import in.simplifymoney.successfulreferral.exception.InvalidReferralCodeException;
import in.simplifymoney.successfulreferral.exception.UserNotFoundException;
import in.simplifymoney.successfulreferral.mapper.UserMapper;
import in.simplifymoney.successfulreferral.model.User;
import in.simplifymoney.successfulreferral.repository.UserRepository;
import in.simplifymoney.successfulreferral.service.UserService;
import in.simplifymoney.successfulreferral.util.IdAndReferralCodeGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {

        Optional<User> userFoundByUserIdOrEmail = userRepository.findByUserIdOrEmail(userRequestDto.getEmail(), userRequestDto.getEmail());

        if(userFoundByUserIdOrEmail.isPresent()) {
            throw new DuplicateEntryException("User exists with the entered id or e-mail");
        }

        User user = userMapper.userRequestDtoToUser(userRequestDto);

        user.setUserId(IdAndReferralCodeGenerator.generateId());
        user.setReferralCode(IdAndReferralCodeGenerator.generateReferralCode());

        if(user.getReferredBy() != null) {
            userRepository.findByReferralCode(user.getReferredBy())
                    .orElseThrow(() -> new InvalidReferralCodeException("Invalid Referral Code"));

            user.setReferredStatus(ReferredStatus.PENDING);
        } else {
            user.setReferredStatus(ReferredStatus.NOT_REFERRED);
        }

        User savedUser = userRepository.save(user);

        return userMapper.userToUserResponseDto(savedUser);
    }

    @Override
    public UserResponseDto getUserByIdOrEmail(String idOrEmail) {
        return userRepository.findByUserIdOrEmail(idOrEmail, idOrEmail)
                .map(userMapper::userToUserResponseDto)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserResponseDto completeUserProfile(String idOrEmail, UserProfileCompleteRequestDto userProfileCompleteRequestDto) {
        if(idOrEmail == null || idOrEmail.isEmpty()) {
            throw new NullPointerException("User id/e-mail cannot be null or empty");
        }

        User user = userRepository.findByUserIdOrEmail(idOrEmail, idOrEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(userProfileCompleteRequestDto.getAddressLine1() != null || !userProfileCompleteRequestDto.getAddressLine1().isEmpty()) {
            user.setAddressLine1(userProfileCompleteRequestDto.getAddressLine1());
        }

        if(userProfileCompleteRequestDto.getAddressLine2() != null || !userProfileCompleteRequestDto.getAddressLine2().isEmpty()) {
            user.setAddressLine2(userProfileCompleteRequestDto.getAddressLine2());
        }

        if(userProfileCompleteRequestDto.getPhoneNumber() != null || !userProfileCompleteRequestDto.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(userProfileCompleteRequestDto.getPhoneNumber());
        }

        if(userProfileCompleteRequestDto.getCity() != null || !userProfileCompleteRequestDto.getCity().isEmpty()) {
            user.setCity(userProfileCompleteRequestDto.getCity());
        }

        if(userProfileCompleteRequestDto.getState() != null || !userProfileCompleteRequestDto.getState().isEmpty()) {
            user.setState(userProfileCompleteRequestDto.getState());
        }

        if(userProfileCompleteRequestDto.getCountry() != null || !userProfileCompleteRequestDto.getCountry().isEmpty()) {
            user.setCountry(userProfileCompleteRequestDto.getCountry());
        }

        if(userProfileCompleteRequestDto.getZipCode() != null || !userProfileCompleteRequestDto.getZipCode().isEmpty()) {
            user.setZipCode(userProfileCompleteRequestDto.getZipCode());
        }

        if(userProfileCompleteRequestDto.getGender() != null) {
            user.setGender(Gender.valueOf(userProfileCompleteRequestDto.getGender()));
        }

        user.setProfileCompleted(true);
        user.setReferredStatus(ReferredStatus.SUCCESSFUL);
        User savedUser = userRepository.save(user);

        // Update the user who referred this user
        User userWhoReferred = userRepository.findByReferralCode(savedUser.getReferredBy())
                .orElseThrow(() -> new InvalidReferralCodeException("User not found"));

        userWhoReferred.setTotalReferrals(userWhoReferred.getTotalReferrals() + 1);
        List<String> referredUsers = userWhoReferred.getReferredUsers();
        referredUsers.add(user.getUserId());

        userWhoReferred.setReferredUsers(referredUsers);
        userRepository.save(userWhoReferred);

        return userMapper.userToUserResponseDto(savedUser);
    }

    @Override
    public List<UserResponseDto> getReferrals(String userIdOrEmail) {
        User referrerUser = userRepository.findByUserIdOrEmail(userIdOrEmail, userIdOrEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return userRepository.findAll().stream()
                .filter(user -> referrerUser.getReferralCode().equals(user.getReferredBy()) && user.getReferredStatus() == ReferredStatus.SUCCESSFUL)
                .map(userMapper::userToUserResponseDto)
                .toList();
    }

    @Override
    public byte[] generateReferralReportCSV(HttpServletResponse response) throws IOException {
        List<User> users = userRepository.findAll();

        // Create CSV data
        StringBuilder csvData = new StringBuilder();
        String[] headers = {"User ID", "Name", "Email", "Referral Code", "Referred By", "Referral Status", "Phone Number", "Address Line 1", "Address Line 2", "City", "State", "Country", "Zip Code", "Gender"};
        csvData.append(String.join(",", headers)).append("\n");

        for (User user : users) {
            csvData.append(user.getUserId()).append(",")
                    .append(user.getName()).append(",")
                    .append(user.getEmail()).append(",")
                    .append(user.getReferralCode()).append(",")
                    .append(user.getReferredBy() != null ? user.getReferredBy() : "N/A").append(",")
                    .append(user.getReferredStatus() != null ? user.getReferredStatus().name() : "N/A").append(",")
                    .append(user.getPhoneNumber() != null ? user.getPhoneNumber() : "N/A").append(",")
                    .append(user.getAddressLine1() != null ? user.getAddressLine1() : "N/A").append(",")
                    .append(user.getAddressLine2() != null ? user.getAddressLine2() : "N/A").append(",")
                    .append(user.getCity() != null ? user.getCity() : "N/A").append(",")
                    .append(user.getState() != null ? user.getState() : "N/A").append(",")
                    .append(user.getCountry() != null ? user.getCountry() : "N/A").append(",")
                    .append(user.getZipCode() != null ? user.getZipCode() : "N/A").append(",")
                    .append(user.getGender() != null ? user.getGender().name() : "N/A").append("\n");
        }

        // Set Response Headers
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=referral_report.csv");

        // Write CSV data to response
        byte[] csvBytes = csvData.toString().getBytes(StandardCharsets.UTF_8);
        response.getOutputStream().write(csvBytes);

        return csvBytes;
    }
}
