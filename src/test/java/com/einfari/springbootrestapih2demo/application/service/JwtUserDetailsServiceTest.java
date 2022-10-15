package com.einfari.springbootrestapih2demo.application.service;

import com.einfari.springbootrestapih2demo.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-15
 **/
@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {

    @Mock
    private UserService userService;
    private JwtUserDetailsService jwtUserDetailsService;

    @BeforeEach
    void setUp() {
        jwtUserDetailsService = new JwtUserDetailsService(userService);
    }

    @Test
    void canLoadUserByUsername() {
        String username = "username";
        UserEntity userEntity = new UserEntity(username, "password");
        User expected = new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());

        when(userService.read(username)).thenReturn(userEntity);
        UserDetails actual = jwtUserDetailsService.loadUserByUsername(username);

        assertThat(actual).isEqualTo(expected);
    }

}