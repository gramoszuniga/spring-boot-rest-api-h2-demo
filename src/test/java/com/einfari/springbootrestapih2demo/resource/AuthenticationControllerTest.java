package com.einfari.springbootrestapih2demo.resource;

import com.einfari.springbootrestapih2demo.common.security.JwtUtils;
import com.einfari.springbootrestapih2demo.resource.model.JwtResponse;
import com.einfari.springbootrestapih2demo.resource.model.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-15
 **/
@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    private AuthenticationController authenticationController;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        authenticationController = new AuthenticationController(authenticationManager, jwtUtils);
    }

    @Test
    void canGetToken() {
        UserRequest userRequest = new UserRequest("username", "password");
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userRequest.getUsername(), userRequest.getPassword(), new ArrayList<>()
        );
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTY2NTg1MTY0MCwiZXhwIjoxNjY1ODU1MjQwfQ" +
                ".wgDe8Z6y3sb75DTVPBvsoD5J73dj0EKMIvp1VVQwByg5avW3OCYoypVE0duIY8gzFW7PTS-7-5XqZVbBmv0-YQ";
        ArgumentCaptor<UsernamePasswordAuthenticationToken> usernamePasswordAuthenticationTokenArgumentCaptor =
                ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ResponseEntity<JwtResponse> expected = new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(),
                userRequest.getPassword()))).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(userRequest.getUsername())).thenReturn(token);
        ResponseEntity<JwtResponse> actual = authenticationController.getToken(userRequest);

        verify(authenticationManager).authenticate(usernamePasswordAuthenticationTokenArgumentCaptor.capture());
        verify(jwtUtils).generateJwtToken(stringArgumentCaptor.capture());
        assertThat(usernamePasswordAuthenticationTokenArgumentCaptor.getValue()).isEqualTo(
                new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword())
        );
        assertThat(stringArgumentCaptor.getValue()).isEqualTo(userRequest.getUsername());
        assertThat(actual).isEqualTo(expected);
    }

}