package com.einfari.springbootrestapih2demo.resource;

import com.einfari.springbootrestapih2demo.common.error.ResourceNotFoundException;
import com.einfari.springbootrestapih2demo.resource.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-09-30
 **/
@SuppressWarnings("unused")
@ControllerAdvice
public class ExceptionController {

    private static final String OOPS_SOMETHING_WENT_WRONG = "Oops, something went wrong.";
    private static final String BAD_CREDENTIALS = "Bad credentials.";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException error) {
        return new ResponseEntity<>(new ErrorResponse(error.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFound(RuntimeException error) {
        return new ResponseEntity<>(new ErrorResponse(BAD_CREDENTIALS), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(RuntimeException error) {
        return new ResponseEntity<>(new ErrorResponse(BAD_CREDENTIALS), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException error) {
        return new ResponseEntity<>(new ErrorResponse(OOPS_SOMETHING_WENT_WRONG), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}