package com.elg.vshop.exception;

import com.elg.vshop.error.AccountErrorResponse;
import javassist.NotFoundException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<AccountErrorResponse> handleNoHandlerFound(NoHandlerFoundException exception, WebRequest request) {
        AccountErrorResponse errorResponse = new AccountErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AccountErrorResponse> handleMethodArgNotValid(MethodArgumentNotValidException exception, WebRequest request) {
        AccountErrorResponse errorResponse = new AccountErrorResponse();
        BindingResult bindingResult = exception.getBindingResult();
        String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(errorMessage);
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AccountErrorResponse> handlePasswordMatchException(PasswordNotMatchException exception) {
        AccountErrorResponse errorResponse = new AccountErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AccountErrorResponse> handleEmailExistException(UserExistException exception) {
        AccountErrorResponse errorResponse = new AccountErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<AccountErrorResponse> handleAllExceptions(Exception exception) {
        AccountErrorResponse errorResponse = new AccountErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<AccountErrorResponse> handleInvalidDataException(InvalidDataException exception) {
        AccountErrorResponse errorResponse = new AccountErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AccountErrorResponse> handleNotFoundException(NotFoundException exception) {
        AccountErrorResponse errorResponse = new AccountErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AccountErrorResponse> handleFileStorageException(FileStorageException exception) {
        AccountErrorResponse errorResponse = new AccountErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MultipartException.class, FileSizeLimitExceededException.class,java.lang.IllegalStateException.class})
    public ResponseEntity<AccountErrorResponse> handleSizeExceededException(final WebRequest request, final MultipartException exception) {
        AccountErrorResponse errorResponse = new AccountErrorResponse();
        errorResponse.setStatus(HttpStatus.PAYLOAD_TOO_LARGE.value());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler
    public ResponseEntity<AccountErrorResponse> handleAnnoncesNotFoundException(AnnoncesNotFoundException exception) {
        AccountErrorResponse errorResponse = new AccountErrorResponse();
        errorResponse.setStatus(HttpStatus.NO_CONTENT.value());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    public ResponseEntity<AccountErrorResponse> handleJwtException(JwtAuthenticationException ex){
        AccountErrorResponse errorResponse = new AccountErrorResponse();
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}
