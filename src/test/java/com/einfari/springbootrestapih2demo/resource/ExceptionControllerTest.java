package com.einfari.springbootrestapih2demo.resource;

import com.einfari.springbootrestapih2demo.common.error.ResourceNotFoundException;
import com.einfari.springbootrestapih2demo.resource.model.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-02
 **/
class ExceptionControllerTest {

    private ExceptionController exceptionController;

    @BeforeEach
    void setUp() {
        exceptionController = new ExceptionController();
    }

    @Test
    void canHandleResourceNotFound() {
        ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException("Game not found.");
        ResponseEntity<ErrorResponse> expected = new ResponseEntity<>(
                new ErrorResponse(resourceNotFoundException.getMessage()), HttpStatus.NOT_FOUND
        );

        ResponseEntity<ErrorResponse> actual = exceptionController.handleResourceNotFound(resourceNotFoundException);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canHandleRuntime() {
        RuntimeException runtimeException = new RuntimeException();
        ResponseEntity<ErrorResponse> expected = new ResponseEntity<>(
                new ErrorResponse("Oops, something went wrong."), HttpStatus.INTERNAL_SERVER_ERROR
        );

        ResponseEntity<ErrorResponse> actual = exceptionController.handleRuntime(runtimeException);

        assertThat(actual).isEqualTo(expected);
    }

}