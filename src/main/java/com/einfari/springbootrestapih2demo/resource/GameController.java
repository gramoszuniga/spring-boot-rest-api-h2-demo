package com.einfari.springbootrestapih2demo.resource;

import com.einfari.springbootrestapih2demo.application.model.Game;
import com.einfari.springbootrestapih2demo.application.model.Games;
import com.einfari.springbootrestapih2demo.application.service.GameService;
import com.einfari.springbootrestapih2demo.resource.mapper.GameRequestMapper;
import com.einfari.springbootrestapih2demo.resource.model.GameRequest;
import com.einfari.springbootrestapih2demo.resource.model.GameResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-09-29
 **/
@SuppressWarnings("unused")
@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class GameController {

    private final GameService gameService;

    @GetMapping("/games")
    public ResponseEntity<Games> getAll() {
        List<Game> gameList = gameService.readAll();
        return new ResponseEntity<>(new Games(gameList), HttpStatus.OK);
    }

    @PostMapping("/games")
    public ResponseEntity<GameResponse> add(@RequestBody @Valid GameRequest gameRequest) {
        Game game = GameRequestMapper.INSTANCE.map(gameRequest);
        Long id = gameService.create(game);
        return new ResponseEntity<>(new GameResponse(id), HttpStatus.CREATED);
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<Game> get(@PathVariable @NonNull Long id) {
        Game game = gameService.read(id);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @PutMapping("/games/{id}")
    public ResponseEntity<Void> put(@PathVariable @NonNull Long id, @RequestBody @Valid GameRequest gameRequest) {
        Game game = GameRequestMapper.INSTANCE.map(gameRequest);
        game.setId(id);
        gameService.update(game);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        gameService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}