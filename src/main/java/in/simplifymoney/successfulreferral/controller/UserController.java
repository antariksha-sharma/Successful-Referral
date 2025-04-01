package in.simplifymoney.successfulreferral.controller;

import in.simplifymoney.successfulreferral.dto.UserProfileCompleteRequestDto;
import in.simplifymoney.successfulreferral.dto.UserRequestDto;
import in.simplifymoney.successfulreferral.dto.UserResponseDto;
import in.simplifymoney.successfulreferral.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.registerUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @GetMapping("/{idOrEmail}")
    public ResponseEntity<UserResponseDto> getUserByIdOrEmail(@PathVariable String idOrEmail) {
        UserResponseDto userResponseDto = userService.getUserByIdOrEmail(idOrEmail);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @PostMapping("/complete-profile/{idOrEmail}")
    public ResponseEntity<UserResponseDto> completeProfile(
            @PathVariable String idOrEmail,
            @RequestBody @Valid UserProfileCompleteRequestDto userProfileCompleteRequestDto) {
        UserResponseDto userResponseDto = userService.completeUserProfile(idOrEmail, userProfileCompleteRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @GetMapping("/referrals/{userIdOrEmail}")
    public ResponseEntity<List<UserResponseDto>> getUserReferrals(@PathVariable String userIdOrEmail) {
        List<UserResponseDto> userResponseDtoList =  userService.getReferrals(userIdOrEmail);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDtoList);
    }

    @GetMapping("/export-referral-report")
    public ResponseEntity<byte[]> exportReferralReport(HttpServletResponse response) throws IOException {
        byte[] report = userService.generateReferralReportCSV(response);
        return ResponseEntity.status(HttpStatus.OK).body(report);
    }

}
