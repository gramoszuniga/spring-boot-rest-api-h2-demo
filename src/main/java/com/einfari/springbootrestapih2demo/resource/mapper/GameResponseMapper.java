package com.einfari.springbootrestapih2demo.resource.mapper;

import com.einfari.springbootrestapih2demo.application.model.Game;
import com.einfari.springbootrestapih2demo.resource.model.GameResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-09-28
 **/
@Mapper
public interface GameResponseMapper {

    GameResponseMapper INSTANCE = Mappers.getMapper(GameResponseMapper.class);

    GameResponse map(Game game);

    Game map(GameResponse gameResponse);

}