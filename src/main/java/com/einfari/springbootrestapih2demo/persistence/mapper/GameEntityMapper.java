package com.einfari.springbootrestapih2demo.persistence.mapper;

import com.einfari.springbootrestapih2demo.application.model.Game;
import com.einfari.springbootrestapih2demo.persistence.entity.GameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-09-28
 **/
@Mapper
public interface GameEntityMapper {

    GameEntityMapper INSTANCE = Mappers.getMapper(GameEntityMapper.class);

    GameEntity map(Game game);

    Game map(GameEntity gameEntity);

}