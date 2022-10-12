package com.einfari.springbootrestapih2demo.resource;

import com.einfari.springbootrestapih2demo.common.security.JwtUtils;
import com.einfari.springbootrestapih2demo.resource.model.JwtResponse;
import com.einfari.springbootrestapih2demo.resource.model.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-10
 **/
@SuppressWarnings("unused")
@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/v1")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getToken(@RequestBody @Valid UserRequest userRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword())
        );
        return new ResponseEntity<>(
                new JwtResponse(jwtUtils.generateJwtToken(userRequest.getUsername())), HttpStatus.OK
        );
    }

}