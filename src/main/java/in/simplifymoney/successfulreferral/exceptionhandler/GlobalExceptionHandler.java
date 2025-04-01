package in.simplifymoney.successfulreferral.exceptionhandler;

import in.simplifymoney.successfulreferral.exception.DuplicateEntryException;
import in.simplifymoney.successfulreferral.exception.InvalidReferralCodeException;
import in.simplifymoney.successfulreferral.exception.UserNotFoundException;
import in.simplifymoney.successfulreferral.exception.errorMessage.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        String errorMessage = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(ApiErrorResponse
                        .builder()
                        .status(HttpStatus.BAD_REQUEST.toString())
                        .message(errorMessage)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(InvalidReferralCodeException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(InvalidReferralCodeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(ApiErrorResponse
                        .builder()
                        .status(HttpStatus.NOT_FOUND.toString())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidDataException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(ApiErrorResponse
                        .builder()
                        .status(HttpStatus.NOT_FOUND.toString())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidDataException(DuplicateEntryException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT.value())
                .body(ApiErrorResponse
                        .builder()
                        .status(HttpStatus.CONFLICT.toString())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
