package com.einfari.springbootrestapih2demo.persistence.repository;

import com.einfari.springbootrestapih2demo.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-12
 **/
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

}