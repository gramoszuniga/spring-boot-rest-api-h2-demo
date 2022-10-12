package com.einfari.springbootrestapih2demo.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-12
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "`user`")
public class UserEntity {

    @Id
    private String username;
    private String password;

}