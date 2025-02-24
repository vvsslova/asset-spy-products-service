package asset.spy.products.service.exception;

import asset.spy.products.service.dto.ErrorResponseDTO;
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
    private ResponseEntity<ErrorResponseDTO> handleException(MethodArgumentNotValidException e) {
        StringBuilder msg = new StringBuilder("Validation error: ");
        e.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    msg.append(error.getField())
                            .append(": ")
                            .append(error.getDefaultMessage())
                            .append("; ");
                });
        log.error(msg.toString(), e);
        ErrorResponseDTO response = new ErrorResponseDTO(msg.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
        log.error("Exception was thrown", e);
        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }
}
