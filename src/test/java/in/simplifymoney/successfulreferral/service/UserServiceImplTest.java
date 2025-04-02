package in.simplifymoney.successfulreferral.service;

import in.simplifymoney.successfulreferral.dto.UserProfileCompleteRequestDto;
import in.simplifymoney.successfulreferral.dto.UserRequestDto;
import in.simplifymoney.successfulreferral.dto.UserResponseDto;
import in.simplifymoney.successfulreferral.enums.ReferredStatus;
import in.simplifymoney.successfulreferral.exception.UserNotFoundException;
import in.simplifymoney.successfulreferral.mapper.UserMapper;
import in.simplifymoney.successfulreferral.model.User;
import in.simplifymoney.successfulreferral.repository.UserRepository;
import in.simplifymoney.successfulreferral.service.impl.UserServiceImpl;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        UserRequestDto userRequestDto = new UserRequestDto();
        User user = new User();
        UserResponseDto userResponseDto = new UserResponseDto();

        when(userRepository.findByUserIdOrEmail(anyString(), anyString())).thenReturn(Optional.empty());
        when(userMapper.userRequestDtoToUser(any(UserRequestDto.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.userToUserResponseDto(any(User.class))).thenReturn(userResponseDto);

        UserResponseDto result = userService.registerUser(userRequestDto);

        assertNotNull(result);
        assertEquals(userResponseDto, result);
    }

    @Test
    public void testGetUserByIdOrEmail() {
        String idOrEmail = "test@example.com";
        User user = new User();
        UserResponseDto userResponseDto = new UserResponseDto();

        when(userRepository.findByUserIdOrEmail(anyString(), anyString())).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponseDto(any(User.class))).thenReturn(userResponseDto);

        UserResponseDto result = userService.getUserByIdOrEmail(idOrEmail);

        assertNotNull(result);
        assertEquals(userResponseDto, result);
    }

    @Test
    public void testGetUserByIdOrEmail_UserNotFound() {
        String idOrEmail = "test@example.com";

        when(userRepository.findByUserIdOrEmail(anyString(), anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByIdOrEmail(idOrEmail));
    }

    @Test
    public void testCompleteUserProfile_UserNotFound() {
        String idOrEmail = "test@example.com";
        UserProfileCompleteRequestDto userProfileCompleteRequestDto = new UserProfileCompleteRequestDto();

        when(userRepository.findByUserIdOrEmail(anyString(), anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.completeUserProfile(idOrEmail, userProfileCompleteRequestDto));
    }

    @Test
    public void testGetReferrals() {
        String userIdOrEmail = "test@example.com";
        User referrerUser = new User();
        referrerUser.setReferralCode("referralCode");
        User user = new User();
        user.setReferredBy("referralCode");
        user.setReferredStatus(ReferredStatus.SUCCESSFUL);
        UserResponseDto userResponseDto = new UserResponseDto();

        when(userRepository.findByUserIdOrEmail(anyString(), anyString())).thenReturn(Optional.of(referrerUser));
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.userToUserResponseDto(any(User.class))).thenReturn(userResponseDto);

        List<UserResponseDto> result = userService.getReferrals(userIdOrEmail);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userResponseDto, result.get(0));
    }

    @Test
    public void testGenerateReferralReportCSV() throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        Mockito.doNothing().when(outputStream).write(any(byte[].class));
        when(response.getOutputStream()).thenReturn(outputStream);

        User user = new User();
        List<User> users = List.of(user);

        when(userRepository.findAll()).thenReturn(users);

        byte[] result = userService.generateReferralReportCSV(response);

        assertNotNull(result);
        verify(response).setContentType("text/csv");
        verify(response).setHeader("Content-Disposition", "attachment; filename=referral_report.csv");
    }
}