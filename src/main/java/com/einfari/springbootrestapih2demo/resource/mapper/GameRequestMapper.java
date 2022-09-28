package com.einfari.springbootrestapih2demo.resource.mapper;

import com.einfari.springbootrestapih2demo.application.model.Game;
import com.einfari.springbootrestapih2demo.resource.model.GameRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-09-28
 **/
@Mapper
public interface GameRequestMapper {

    GameRequestMapper INSTANCE = Mappers.getMapper(GameRequestMapper.class);

    GameRequest map(Game game);

    Game map(GameRequest gameRequest);

}