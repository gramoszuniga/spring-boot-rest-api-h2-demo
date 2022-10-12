package com.einfari.springbootrestapih2demo.application.service;

import com.einfari.springbootrestapih2demo.persistence.entity.UserEntity;
import com.einfari.springbootrestapih2demo.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-12
 **/
@AllArgsConstructor
@Service
public class UserService {

    public static final String USER_NOT_FOUND = "User not found.";
    private final UserRepository userRepository;

    public UserEntity read(String username) {
        Optional<UserEntity> userEntity = userRepository.findById(username);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }
        return userEntity.get();
    }

}