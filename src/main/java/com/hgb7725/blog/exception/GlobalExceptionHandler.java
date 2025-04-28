package com.hgb7725.blog.exception;

import com.hgb7725.blog.payload.ErrorDetail;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleException(Exception exception, WebRequest webRequest) {
        String path = webRequest.getDescription(false).replace("uri=", "");
        ErrorDetail errors = new ErrorDetail(
                new Date(),
//                "An unexpected internal server error occurred.",
                exception.getMessage(),
                List.of("Please contact support if the problem persists."),
                path);
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetail> handleBlogAPIException(BlogAPIException exception, WebRequest webRequest) {
        String path = webRequest.getDescription(false).replace("uri=", "");
        ErrorDetail errors = new ErrorDetail(
                new Date(),
                exception.getMessage(),
                List.of(path),
                path);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetail> handleBadCredentialsException(BadCredentialsException exception,
                                                                     WebRequest webRequest) {
        String path = webRequest.getDescription(false).replace("uri=", "");

        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                "Invalid username/email or password.",
                List.of("Authentication failed due to invalid credentials."),
                path
        );

        return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                             WebRequest webRequest) {
        List<String> errors = exception
                                .getBindingResult().getFieldErrors()
                                .stream().map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .toList();
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                "Validation Failed",
                errors,
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                       WebRequest webRequest) {
        String path = webRequest.getDescription(false).replace("uri=", "");
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                exception.getMessage(),
                List.of(path),
                path);
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetail> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                "Database integrity error: maybe duplicate or constraint violation.",
                List.of(ex.getMessage()),
                path
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorDetail> handleAuthorizationDeniedException(AuthorizationDeniedException exception, WebRequest webRequest) {
        ErrorDetail errorDetails = new ErrorDetail(
                new Date(),
                exception.getMessage(),
                List.of(webRequest.getDescription(false)),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

}
