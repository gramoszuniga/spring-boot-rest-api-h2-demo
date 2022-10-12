package com.einfari.springbootrestapih2demo.resource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-10
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotNull(message = "User name is required.")
    @NotBlank(message = "User name cannot be empty.")
    private String username;
    @NotNull(message = "Password is required.")
    @NotBlank(message = "Password cannot be empty.")
    private String password;

}