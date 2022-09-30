package com.einfari.springbootrestapih2demo.application.service;

import com.einfari.springbootrestapih2demo.application.model.Game;
import com.einfari.springbootrestapih2demo.common.error.ResourceNotFoundException;
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

    private static final String GAME_NOT_FOUND = "Game not found.";
    private final GameRepository gameRepository;

    public Long create(Game game) {
        GameEntity gameEntity = gameRepository.save(GameEntityMapper.INSTANCE.map(game));
        return gameEntity.getId();
    }

    public List<Game> readAll() {
        List<GameEntity> gameEntityList = gameRepository.findAll();
        return gameEntityList.stream().map(GameEntityMapper.INSTANCE::map).collect(Collectors.toList());
    }

    public Game read(Long id) {
        Optional<GameEntity> gameEntity = gameRepository.findById(id);
        if (gameEntity.isEmpty()) {
            throw new ResourceNotFoundException(GAME_NOT_FOUND);
        }
        return GameEntityMapper.INSTANCE.map(gameEntity.get());
    }

    public void update(Game game) {
        Optional<GameEntity> gameEntity = gameRepository.findById(game.getId());
        if (gameEntity.isEmpty()) {
            throw new ResourceNotFoundException(GAME_NOT_FOUND);
        }
        gameRepository.save(GameEntityMapper.INSTANCE.map(game));
    }

    public void delete(Long id) {
        Optional<GameEntity> gameEntity = gameRepository.findById(id);
        if (gameEntity.isEmpty()) {
            throw new ResourceNotFoundException(GAME_NOT_FOUND);
        }
        gameRepository.deleteById(id);
    }

}