package com.einfari.springbootrestapih2demo.resource;

import com.einfari.springbootrestapih2demo.application.model.Game;
import com.einfari.springbootrestapih2demo.application.model.Games;
import com.einfari.springbootrestapih2demo.application.service.GameService;
import com.einfari.springbootrestapih2demo.resource.mapper.GameRequestMapper;
import com.einfari.springbootrestapih2demo.resource.model.GameRequest;
import com.einfari.springbootrestapih2demo.resource.model.GameResponse;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.ReplaceOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-01
 **/
@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    private GameController gameController;
    @Mock
    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameController = new GameController(gameService);
    }

    @Test
    void canGetAll() {
        List<Game> gameList = List.of(
                new Game(83L, "93", "Super Smash Bros. Ultimate", "Switch", "Dec 7, 2018", "8.6",
                        "/game/switch/super-smash-bros-ultimate", "E10+"),
                new Game(994L, "92", "Banjo-Kazooie", "N64", "May 31, 1998", "9.1",
                        "/game/nintendo-64/banjo-kazooie", "E")
        );
        ResponseEntity<Games> expected = new ResponseEntity<>(new Games(gameList), HttpStatus.OK);

        when(gameService.readAll()).thenReturn(gameList);
        ResponseEntity<Games> actual = gameController.getAll();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canGetAllEmpty() {
        List<Game> gameList = new ArrayList<>();
        ResponseEntity<Games> expected = new ResponseEntity<>(new Games(gameList), HttpStatus.OK);

        when(gameService.readAll()).thenReturn(gameList);
        ResponseEntity<Games> actual = gameController.getAll();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canAdd() {
        GameRequest gameRequest = new GameRequest("92", "Unreal Tournament (1999)", "PC", "Nov 30, 1999", "9.0",
                "/game/pc/unreal-tournament-1999", "M");
        Long id = 1027L;
        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);
        ResponseEntity<GameResponse> expected = new ResponseEntity<>(new GameResponse(id), HttpStatus.CREATED);

        when(gameService.create(GameRequestMapper.INSTANCE.map(gameRequest))).thenReturn(id);
        ResponseEntity<GameResponse> actual = gameController.add(gameRequest);

        verify(gameService).create(gameArgumentCaptor.capture());
        assertThat(gameArgumentCaptor.getValue()).isEqualTo(GameRequestMapper.INSTANCE.map(gameRequest));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canGet() {
        Game game = new Game(992L, "99", "The Legend of Zelda: Ocarina of Time", "N64", "Nov 23, 1998", "9.1",
                "/game/nintendo-64/the-legend-of-zelda-ocarina-of-time", "E");
        Long id = 992L;
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ResponseEntity<Game> expected = new ResponseEntity<>(game, HttpStatus.OK);

        when(gameService.read(id)).thenReturn(game);
        ResponseEntity<Game> actual = gameController.get(id);

        verify(gameService).read(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(id);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canPatch() {
        Long id = 1023L;
        JsonPatch jsonPatch = new JsonPatch(Arrays.asList(
                new ReplaceOperation(
                        JsonPointer.of("title"), new TextNode("The Legend of Zelda: Tears of the Kingdom")
                ),
                new ReplaceOperation(
                        JsonPointer.of("date"), new TextNode("May 12, 2023")
                ),
                new ReplaceOperation(
                        JsonPointer.of("link"), new TextNode("/game/switch/the-legend-of-zelda-tears-of-the-kingdom")
                ))
        );
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<JsonPatch> jsonPatchArgumentCaptor = ArgumentCaptor.forClass(JsonPatch.class);
        ResponseEntity<Object> expected = new ResponseEntity<>(HttpStatus.OK);

        ResponseEntity<Void> actual = gameController.patch(id, jsonPatch);

        verify(gameService).updatePartial(longArgumentCaptor.capture(), jsonPatchArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(id);
        assertThat(jsonPatchArgumentCaptor.getValue()).isEqualTo(jsonPatch);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canPut() {
        Long id = 992L;
        GameRequest gameRequest = new GameRequest("100", "The Legend of Zelda: Ocarina of Time", "N64", "Nov 23, 1998",
                "10", "/gameRequest/nintendo-64/the-legend-of-zelda-ocarina-of-time", "E");
        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);
        Game game = GameRequestMapper.INSTANCE.map(gameRequest);
        game.setId(id);
        ResponseEntity<Object> expected = new ResponseEntity<>(HttpStatus.OK);

        ResponseEntity<Void> actual = gameController.put(id, gameRequest);

        verify(gameService).update(gameArgumentCaptor.capture());
        assertThat(gameArgumentCaptor.getValue()).isEqualTo(game);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canDelete() {
        Long id = 1L;
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ResponseEntity<Object> expected = new ResponseEntity<>(HttpStatus.OK);

        ResponseEntity<Void> actual = gameController.delete(id);

        verify(gameService).delete(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(id);
        assertThat(actual).isEqualTo(expected);
    }

}