package com.einfari.springbootrestapih2demo.application.service;

import com.einfari.springbootrestapih2demo.persistence.entity.UserEntity;
import com.einfari.springbootrestapih2demo.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-15
 **/
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void canRead() {
        String username = "username";
        String password = "password";
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(username, password));
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        UserEntity expected = new UserEntity(username, password);

        when(userRepository.findById(username)).thenReturn(userEntity);
        UserEntity actual = userService.read(username);

        verify(userRepository).findById(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue()).isEqualTo(username);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void cannotRead() {
        String username = "username";
        Optional<UserEntity> userEntity = Optional.empty();
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(userRepository.findById(username)).thenReturn(userEntity);

        assertThatThrownBy(() -> userService.read(username)).isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found.");
        verify(userRepository).findById(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue()).isEqualTo(username);
    }

}