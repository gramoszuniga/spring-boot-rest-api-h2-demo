package com.einfari.springbootrestapih2demo.application.service;

import com.einfari.springbootrestapih2demo.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-10
 **/
@AllArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userService.read(username);
        return new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());
    }

}