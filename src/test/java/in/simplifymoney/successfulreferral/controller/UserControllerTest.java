package in.simplifymoney.successfulreferral.controller;

import in.simplifymoney.successfulreferral.dto.UserProfileCompleteRequestDto;
import in.simplifymoney.successfulreferral.dto.UserRequestDto;
import in.simplifymoney.successfulreferral.dto.UserResponseDto;
import in.simplifymoney.successfulreferral.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUser() {
        UserRequestDto userRequestDto = new UserRequestDto();
        UserResponseDto userResponseDto = new UserResponseDto();
        when(userService.registerUser(any(UserRequestDto.class))).thenReturn(userResponseDto);

        ResponseEntity<UserResponseDto> response = userController.saveUser(userRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userResponseDto, response.getBody());
    }

    @Test
    public void testGetUserByIdOrEmail() {
        String idOrEmail = "test@example.com";
        UserResponseDto userResponseDto = new UserResponseDto();
        when(userService.getUserByIdOrEmail(idOrEmail)).thenReturn(userResponseDto);

        ResponseEntity<UserResponseDto> response = userController.getUserByIdOrEmail(idOrEmail);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponseDto, response.getBody());
    }

    @Test
    public void testCompleteProfile() {
        String idOrEmail = "test@example.com";
        UserProfileCompleteRequestDto userProfileCompleteRequestDto = new UserProfileCompleteRequestDto();
        UserResponseDto userResponseDto = new UserResponseDto();
        when(userService.completeUserProfile(any(String.class), any(UserProfileCompleteRequestDto.class))).thenReturn(userResponseDto);

        ResponseEntity<UserResponseDto> response = userController.completeProfile(idOrEmail, userProfileCompleteRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponseDto, response.getBody());
    }

    @Test
    public void testGetUserReferrals() {
        String userIdOrEmail = "test@example.com";
        List<UserResponseDto> userResponseDtoList = List.of(new UserResponseDto());
        when(userService.getReferrals(userIdOrEmail)).thenReturn(userResponseDtoList);

        ResponseEntity<List<UserResponseDto>> response = userController.getUserReferrals(userIdOrEmail);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponseDtoList, response.getBody());
    }

    @Test
    public void testExportReferralReport() throws IOException {
        HttpServletResponse response = null;
        byte[] report = new byte[0];
        when(userService.generateReferralReportCSV(response)).thenReturn(report);

        ResponseEntity<byte[]> responseEntity = userController.exportReferralReport(response);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(report, responseEntity.getBody());
    }
}
