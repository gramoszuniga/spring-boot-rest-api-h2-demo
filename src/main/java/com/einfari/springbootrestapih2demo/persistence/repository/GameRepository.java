package com.einfari.springbootrestapih2demo.persistence.repository;

import com.einfari.springbootrestapih2demo.persistence.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-09-28
 **/
@SuppressWarnings("unused")
@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

}