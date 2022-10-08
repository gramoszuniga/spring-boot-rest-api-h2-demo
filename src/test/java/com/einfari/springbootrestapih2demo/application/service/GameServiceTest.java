package com.einfari.springbootrestapih2demo.application.service;

import com.einfari.springbootrestapih2demo.application.model.Game;
import com.einfari.springbootrestapih2demo.common.error.ResourceNotFoundException;
import com.einfari.springbootrestapih2demo.persistence.entity.GameEntity;
import com.einfari.springbootrestapih2demo.persistence.mapper.GameEntityMapper;
import com.einfari.springbootrestapih2demo.persistence.repository.GameRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-09-30
 **/
@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    private static final String GAME_NOT_FOUND = "Game not found.";
    private GameService gameService;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        gameService = new GameService(gameRepository, objectMapper);
    }

    @Test
    void canCreate() {
        Game game = new Game(null, "92", "Unreal Tournament (1999)", "PC", "Nov 30, 1999", "9.0",
                "/game/pc/unreal-tournament-1999", "M");
        GameEntity gameEntity = new GameEntity(1027L, "92", "Unreal Tournament (1999)", "PC", "Nov 30, 1999", "9.0",
                "/game/pc/unreal-tournament-1999", "M");
        ArgumentCaptor<GameEntity> gameArgumentCaptor = ArgumentCaptor.forClass(GameEntity.class);
        Long expected = gameEntity.getId();

        when(gameRepository.save(GameEntityMapper.INSTANCE.map(game))).thenReturn(gameEntity);
        Long actual = gameService.create(game);

        verify(gameRepository).save(gameArgumentCaptor.capture());
        assertThat(gameArgumentCaptor.getValue()).isEqualTo(GameEntityMapper.INSTANCE.map(game));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canReadAll() {
        List<GameEntity> gameEntityList = List.of(
                new GameEntity(83L, "93", "Super Smash Bros. Ultimate", "Switch", "Dec 7, 2018", "8.6",
                        "/game/switch/super-smash-bros-ultimate", "E10+"),
                new GameEntity(994L, "92", "Banjo-Kazooie", "N64", "May 31, 1998", "9.1",
                        "/game/nintendo-64/banjo-kazooie", "E")
        );
        List<Game> expected = gameEntityList.stream().map(GameEntityMapper.INSTANCE::map).collect(Collectors
                .toList());

        when(gameRepository.findAll()).thenReturn(gameEntityList);
        List<Game> gameList = gameService.readAll();

        assertThat(gameList).isEqualTo(expected);
    }

    @Test
    void canReadEmpty() {
        List<GameEntity> gameEntityList = new ArrayList<>();
        List<Game> expected = new ArrayList<>();

        when(gameRepository.findAll()).thenReturn(gameEntityList);
        List<Game> gameList = gameService.readAll();

        assertThat(gameList).isEqualTo(expected);
    }

    @Test
    void canRead() {
        Long id = 992L;
        Optional<GameEntity> gameEntity = Optional.of(new GameEntity(id, "99", "The Legend of Zelda: Ocarina of Time",
                "N64", "Nov 23, 1998", "9.1", "/game/nintendo-64/the-legend-of-zelda-ocarina-of-time", "E"));
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        Game expected = GameEntityMapper.INSTANCE.map(gameEntity.get());

        when(gameRepository.findById(id)).thenReturn(gameEntity);
        Game actual = gameService.read(id);

        verify(gameRepository).findById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(id);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void cannotReadWillThrow() {
        Long id = 1027L;
        Optional<GameEntity> gameEntity = Optional.empty();
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        when(gameRepository.findById(id)).thenReturn(gameEntity);

        assertThatThrownBy(() -> gameService.read(id)).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(GAME_NOT_FOUND);
        verify(gameRepository).findById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(id);
    }

    @Test
    void canUpdate() {
        Game game = new Game(992L, "100", "The Legend of Zelda: Ocarina of Time", "N64", "Nov 23, 1998", "10",
                "/game/nintendo-64/the-legend-of-zelda-ocarina-of-time", "E");
        Optional<GameEntity> gameEntity = Optional.of(new GameEntity(992L, "99", "The Legend of Zelda: Ocarina of Time",
                "N64", "Nov 23, 1998", "9.1", "/game/nintendo-64/the-legend-of-zelda-ocarina-of-time", "E"));
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        when(gameRepository.findById(game.getId())).thenReturn(gameEntity);
        gameService.update(game);

        verify(gameRepository).findById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(game.getId());
        verify(gameRepository, times(1)).save(GameEntityMapper.INSTANCE.map(game));
    }

    @Test
    void cannotUpdateWillThrow() {
        Game game = new Game(992L, "100", "The Legend of Zelda: Ocarina of Time", "N64", "Nov 23, 1998", "10",
                "/game/nintendo-64/the-legend-of-zelda-ocarina-of-time", "E");
        Optional<GameEntity> gameEntity = Optional.empty();
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        when(gameRepository.findById(game.getId())).thenReturn(gameEntity);

        assertThatThrownBy(() -> gameService.update(game)).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(GAME_NOT_FOUND);
        verify(gameRepository).findById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(game.getId());
        verify(gameRepository, never()).save(any());
    }

    @SuppressWarnings("unchecked")
    @Test
    void canUpdatePartial() throws JsonProcessingException {
        Long id = 1023L;
        Optional<GameEntity> gameEntity = Optional.of(new GameEntity(id, null,
                "The Legend of Zelda: Breath of the Wild Sequel", "Switch", "TBA", null,
                "/game/switch/the-legend-of-zelda-breath-of-the-wild-sequel", null));
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
        ObjectNode objectNode = new ObjectNode(JsonNodeFactory.instance);
        objectNode.put("id", id);
        objectNode.putNull("metaScore");
        objectNode.put("title", "The Legend of Zelda: Tears of the Kingdom");
        objectNode.put("platform", "Switch");
        objectNode.put("date", "May 12, 2023");
        objectNode.putNull("userScore");
        objectNode.put("link", "/game/switch/the-legend-of-zelda-tears-of-the-kingdom");
        objectNode.putNull("esrbRating");
        GameEntity patchedGameEntity = new GameEntity(id, null, "The Legend of Zelda: Tears of the Kingdom", "Switch",
                "May 12, 2023", null, "/game/switch/the-legend-of-zelda-tears-of-the-kingdom", null);
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<GameEntity> gameEntityArgumentCaptor = ArgumentCaptor.forClass(GameEntity.class);
        ArgumentCaptor<Class<JsonNode>> jsonNodeClassArgumentCaptor = ArgumentCaptor.forClass(Class.class);
        ArgumentCaptor<JsonNode> jsonNodeArgumentCaptor = ArgumentCaptor.forClass(JsonNode.class);
        ArgumentCaptor<Class<GameEntity>> gameEntityClassArgumentCaptor = ArgumentCaptor.forClass(Class.class);

        when(gameRepository.findById(id)).thenReturn(gameEntity);
        when(objectMapper.convertValue(gameEntity.get(), JsonNode.class)).thenReturn(objectNode);
        when(objectMapper.treeToValue(objectNode, GameEntity.class)).thenReturn(patchedGameEntity);
        gameService.updatePartial(id, jsonPatch);

        verify(gameRepository).findById(longArgumentCaptor.capture());
        verify(objectMapper).convertValue(gameEntityArgumentCaptor.capture(), jsonNodeClassArgumentCaptor.capture());
        verify(objectMapper).treeToValue(jsonNodeArgumentCaptor.capture(), gameEntityClassArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(id);
        assertThat(gameEntityArgumentCaptor.getValue()).isEqualTo(gameEntity.get());
        assertThat(jsonNodeClassArgumentCaptor.getValue()).isEqualTo(JsonNode.class);
        assertThat(jsonNodeArgumentCaptor.getValue()).isEqualTo(objectNode);
        assertThat(gameEntityClassArgumentCaptor.getValue()).isEqualTo(GameEntity.class);
        verify(gameRepository, times(1)).save(patchedGameEntity);
    }

    @SuppressWarnings("unchecked")
    @Test
    void cannotUpdatePartialWillThrowResourceNotFound() throws JsonProcessingException {
        Long id = 1023L;
        Optional<GameEntity> gameEntity = Optional.empty();
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

        when(gameRepository.findById(id)).thenReturn(gameEntity);

        assertThatThrownBy(() -> gameService.updatePartial(id, jsonPatch)).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(GAME_NOT_FOUND);
        verify(gameRepository).findById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(id);
        verify(objectMapper, never()).convertValue(any(), any(Class.class));
        verify(objectMapper, never()).treeToValue(any(), any(Class.class));
        verify(gameRepository, never()).save(any());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void cannotUpdatePartialWillThrowJsonPatch() throws JsonProcessingException {
        Long id = 1023L;
        Optional<GameEntity> gameEntity = Optional.of(new GameEntity(id, null,
                "The Legend of Zelda: Breath of the Wild Sequel", "Switch", "TBA", null,
                "/game/switch/the-legend-of-zelda-breath-of-the-wild-sequel", null));
        JsonPatch jsonPatch = new JsonPatch(Arrays.asList(
                new ReplaceOperation(
                        JsonPointer.of(""), new TextNode("The Legend of Zelda: Tears of the Kingdom")
                ),
                new ReplaceOperation(
                        JsonPointer.of("date"), new TextNode("May 12, 2023")
                ),
                new ReplaceOperation(
                        JsonPointer.of("link"), new TextNode("/game/switch/the-legend-of-zelda-tears-of-the-kingdom")
                ))
        );
        ObjectNode objectNode = new ObjectNode(JsonNodeFactory.instance);
        objectNode.put("id", id);
        objectNode.putNull("metaScore");
        objectNode.put("title", "The Legend of Zelda: Tears of the Kingdom");
        objectNode.put("platform", "Switch");
        objectNode.put("date", "May 12, 2023");
        objectNode.putNull("userScore");
        objectNode.put("link", "/game/switch/the-legend-of-zelda-tears-of-the-kingdom");
        objectNode.putNull("esrbRating");
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        when(gameRepository.findById(id)).thenReturn(gameEntity);
        when(objectMapper.convertValue(gameEntity.get(), JsonNode.class)).thenReturn(objectNode);

        assertThatThrownBy(() -> gameService.updatePartial(id, jsonPatch)).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no such path in target JSON document");
        verify(gameRepository).findById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(id);
        verify(objectMapper, never()).treeToValue(any(), any(Class.class));
        verify(gameRepository, never()).save(any());
    }

    @Test
    public void cannotUpdatePartialWillThrowJsonProcessing() throws JsonProcessingException {
        Long id = 1023L;
        Optional<GameEntity> gameEntity = Optional.of(new GameEntity(id, null,
                "The Legend of Zelda: Breath of the Wild Sequel", "Switch", "TBA", null,
                "/game/switch/the-legend-of-zelda-breath-of-the-wild-sequel", null));
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
        ObjectNode objectNode = new ObjectNode(JsonNodeFactory.instance);
        objectNode.put("id", id);
        objectNode.putNull("metaScore");
        objectNode.put("title", "The Legend of Zelda: Tears of the Kingdom");
        objectNode.put("platform", "Switch");
        objectNode.put("date", "May 12, 2023");
        objectNode.putNull("userScore");
        objectNode.put("link", "/game/switch/the-legend-of-zelda-tears-of-the-kingdom");
        objectNode.putNull("esrbRating");

        when(gameRepository.findById(id)).thenReturn(gameEntity);
        when(objectMapper.convertValue(gameEntity.get(), JsonNode.class)).thenReturn(objectNode);
        when(objectMapper.treeToValue(objectNode, GameEntity.class)).thenThrow(JsonProcessingException.class);

        assertThatThrownBy(() -> gameService.updatePartial(id, jsonPatch)).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("N/A");
        verify(gameRepository, never()).save(any());
    }

    @Test
    void canDelete() {
        Long id = 1L;
        Optional<GameEntity> gameEntity = Optional.of(new GameEntity(id, null, "Super Mario 3D World + Bowser's Fury",
                "Switch", "Feb 12, 2021", null, "/game/switch/super-mario-3d-world-+-bowsers-fury", null));
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        when(gameRepository.findById(id)).thenReturn(gameEntity);
        gameService.delete(id);

        verify(gameRepository).findById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(id);
        verify(gameRepository, times(1)).deleteById(id);
    }

    @Test
    void cannotDeleteWillThrow() {
        Long id = 1027L;
        Optional<GameEntity> gameEntity = Optional.empty();
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        when(gameRepository.findById(id)).thenReturn(gameEntity);

        assertThatThrownBy(() -> gameService.delete(id)).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(GAME_NOT_FOUND);
        verify(gameRepository).findById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(id);
        verify(gameRepository, never()).deleteById(any());
    }

}