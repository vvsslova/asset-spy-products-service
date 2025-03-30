package asset.spy.products.service.exception;

import asset.spy.auth.lib.exception.JwtValidationException;
import asset.spy.products.service.dto.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<List<ErrorResponseDto>> handleException(MethodArgumentNotValidException e) {
        List<ErrorResponseDto> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorResponseDto(error.getDefaultMessage()))
                .toList();
        log.error("Validation exception", e);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    private ResponseEntity<ErrorResponseDto> handleException(EntityAlreadyExistsException e) {
        ErrorResponseDto response = new ErrorResponseDto(e.getMessage());
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ErrorResponseDto> handleException(EntityNotFoundException e) {
        ErrorResponseDto response = new ErrorResponseDto("Entity does not exists");
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtValidationException.class)
    private ResponseEntity<ErrorResponseDto> handleException(JwtValidationException e) {
        ErrorResponseDto response = new ErrorResponseDto(e.getMessage());
        log.error("Jwt validation exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<ErrorResponseDto> handleException(AccessDeniedException e) {
        ErrorResponseDto response = new ErrorResponseDto("Access denied");
        log.error("Access denied", e);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<ErrorResponseDto> handleException(AuthenticationException e) {
        ErrorResponseDto response = new ErrorResponseDto(e.getMessage());
        log.error("Authentication exception", e);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        ErrorResponseDto response = new ErrorResponseDto(e.getMessage());
        log.error("Exception was thrown", e);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}