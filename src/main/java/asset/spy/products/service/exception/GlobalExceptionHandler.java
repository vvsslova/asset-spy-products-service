package asset.spy.products.service.exception;

import asset.spy.products.service.dto.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponseDto> handleException(MethodArgumentNotValidException e) {
        StringBuilder msg = new StringBuilder("Validation error: ");
        e.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    msg.append(error.getField())
                            .append(": ")
                            .append(error.getDefaultMessage())
                            .append("; ");
                });
        log.error(msg.toString(), e);
        ErrorResponseDto response = new ErrorResponseDto(msg.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        ErrorResponseDto response = new ErrorResponseDto(e.getMessage());
        log.error("Exception was thrown", e);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}