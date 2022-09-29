package com.einfari.springbootrestapih2demo.persistence.mapper;

import com.einfari.springbootrestapih2demo.application.model.Game;
import com.einfari.springbootrestapih2demo.persistence.entity.GameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-09-28
 **/
@Mapper
public interface GameEntityMapper {

    GameEntityMapper INSTANCE = Mappers.getMapper(GameEntityMapper.class);

    @Mapping(source = "metaScore", target = "meta_score")
    @Mapping(source = "userScore", target = "user_score")
    @Mapping(source = "esrbRating", target = "esrb_rating")
    GameEntity map(Game game);

    @Mapping(source = "meta_score", target = "metaScore")
    @Mapping(source = "user_score", target = "userScore")
    @Mapping(source = "esrb_rating", target = "esrbRating")
    Game map(GameEntity gameEntity);

}