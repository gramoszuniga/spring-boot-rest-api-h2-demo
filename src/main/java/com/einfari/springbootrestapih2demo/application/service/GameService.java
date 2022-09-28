package com.einfari.springbootrestapih2demo.application.service;

import com.einfari.springbootrestapih2demo.application.model.Game;
import com.einfari.springbootrestapih2demo.persistence.entity.GameEntity;
import com.einfari.springbootrestapih2demo.persistence.mapper.GameEntityMapper;
import com.einfari.springbootrestapih2demo.persistence.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-09-28
 **/
@AllArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;

    public Long create(Game game) {
        GameEntity gameEntity = GameEntityMapper.INSTANCE.map(game);
        gameEntity = gameRepository.save(gameEntity);
        return gameEntity.getId();
    }

    public List<Game> readAll() {
        List<GameEntity> gameEntityList = gameRepository.findAll();
        return gameEntityList.stream().map(GameEntityMapper.INSTANCE::map).collect(Collectors.toList());
    }

    public Game read(Long id) {
        Optional<GameEntity> gameEntity = gameRepository.findById(id);
        return gameEntity.map(GameEntityMapper.INSTANCE::map).orElseThrow(
                () -> new RuntimeException("Game not found.")
        );
    }

    public void delete(Long id) {
        gameRepository.deleteById(id);
    }

}