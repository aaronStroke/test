package mx.loal.pharmacy_admin_api.exceptions.controlleradvice;

import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.exceptions.*;
import mx.loal.pharmacy_admin_api.payload.ExceptionDto;
import mx.loal.pharmacy_admin_api.utils.constants.ExceptionMessageConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ExceptionDto> buildResponseEntity(HttpStatus status, String message, WebRequest request) {
        var error = ExceptionDto
            .builder()
            .status(status.value())
            .message(message)
            .error(status.getReasonPhrase())
            .path(request.getDescription(false).substring(4)) // Remover "uri="
            .timestamp(LocalDateTime.now())
            .build();
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionDto> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        log.error("AuthenticationException: {}", ex.getMessage(), ex);
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return buildResponseEntity(status, ex.getMessage(), request);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ExceptionDto> handleAppException(AppException ex, WebRequest request) {
        log.error("AppException: {}", ex.getMessage(), ex);
        HttpStatus status = HttpStatus.valueOf(ex.getResponseCode());
        return buildResponseEntity(status, ex.getMessage(), request);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ExceptionDto> handleNoContentException(NoContentException ex, WebRequest request) {
        log.warn("NoContentException: {}", ex.getMessage());
        return buildResponseEntity(HttpStatus.NO_CONTENT, ex.getMessage(), request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException ex, WebRequest request) {
        log.warn("NotFoundException: {}", ex.getMessage());
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ExceptionDto> handleRequestException(RequestException ex, WebRequest request) {
        log.warn("RequestException: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        log.warn("MethodArgumentNotValidException: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionDto> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, WebRequest request) {
        log.warn("HttpMediaTypeNotSupportedException: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ExceptionDto> handleIllegalStateExceptionn(IllegalStateException ex, WebRequest request) {
        log.warn("IllegalStateException: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }


    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ExceptionDto> handleDuplicateKeyException(DuplicateKeyException ex, WebRequest request) {
        log.warn("DuplicateKeyException: {}", ex.getMessage());
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(UnauthorizedAppointmentException.class)
    public ResponseEntity<ExceptionDto> handleUnauthorizedAppointmentException(UnauthorizedAppointmentException ex, WebRequest request) {
        log.warn("UnauthorizedAppointmentException: {}", ex.getMessage());
        return buildResponseEntity(HttpStatus.FORBIDDEN, ex.getMessage(), request);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ExceptionDto> handleInternalServerErrorException(InternalServerErrorException ex, WebRequest request) {
        log.error("InternalServerErrorException: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionMessageConstants.INTERNAL_SERVER_ERROR_MSG, request);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionDto> handleNullPointerException(NullPointerException ex, WebRequest request) {
        log.error("NullPointerException: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionMessageConstants.INTERNAL_SERVER_ERROR_MSG, request);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDto> handleRuntimeException(RuntimeException ex, WebRequest request) {
        log.error("RuntimeException: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionMessageConstants.INTERNAL_SERVER_ERROR_MSG, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleGenericException(Exception ex, WebRequest request) {
        log.error("Exception: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionMessageConstants.INTERNAL_SERVER_ERROR_MSG, request);
    }
}
