package nl.tudelft.jpacman.npc.ghost;


import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


public class ClydeTest {

    private GhostMapParser parser;
    private Player pacman;
    /**
     *
     */
    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        PointCalculator pointCalculator = new DefaultPointCalculator();
        GhostFactory ghostFactory = new GhostFactory(sprites);
        BoardFactory boardFactory = new BoardFactory(sprites);
        LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory, pointCalculator);
        parser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        PlayerFactory playerFactory = new PlayerFactory(sprites);
        pacman = playerFactory.createPacMan();
    }

    /**
     *
     */
    @Test
    public void testReachableFar() {
        ArrayList<String> map = new ArrayList<>(List.of(
                "############",
                "#P........C.",
                "############"
        ));
        Level level = parser.parseMap(map);

        // find clyde in the board
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());

        // comment these two lines if no player existent
        level.registerPlayer(pacman); // register player
        pacman.setDirection(Direction.EAST); // set direction pacman facing

        Optional<Direction> next = clyde.nextAiMove(); // call func which dir want to move
        assertTrue(next.isPresent()); // whether or not empty, or whether or not a dir there
        assertSame(Direction.WEST, next.get());
    }

    @Test
    public void testMoveOffward(){
        ArrayList<String> map = new ArrayList<>(List.of(
                "############",
                "#P.....C....",
                "############"
        ));
        Level level = parser.parseMap(map);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.EAST);

        Optional<Direction> next = clyde.nextAiMove();
        assertTrue(next.isPresent());
        assertSame(Direction.EAST, next.get());
    }

    @Test
    public void noPathTestWithoutFarAway(){
        ArrayList<String> map = new ArrayList<>(List.of(
                "############",
                "#P....#C....",
                "############"
        ));
        Level level = parser.parseMap(map);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.EAST);

        Optional<Direction> next = clyde.nextAiMove();
        assertThat(Optional.empty()).isEqualTo(next);
    }

  @Test
  public void noPathTestWithFarAway() {
      ArrayList<String> map = new ArrayList<>(List.of(
              "############",
              "#P........#C",
              "############"
      ));
      Level level = parser.parseMap(map);

      Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
      level.registerPlayer(pacman);
      pacman.setDirection(Direction.EAST);

      Optional<Direction> next = clyde.nextAiMove();
      assertThat(Optional.empty()).isEqualTo(next);
  }

  @Test
  public void noPlayerWithFaraway() {
      ArrayList<String> map = new ArrayList<>(List.of(
              "############",
              "#........#C#",
              "############"
      ));
      Level level = parser.parseMap(map);

      Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());


      Optional<Direction> next = clyde.nextAiMove();
      assertThat(Optional.empty()).isEqualTo(next);
  }

    @Test
    public void noPlayerWithoutFaraway() {
        ArrayList<String> map = new ArrayList<>(List.of(
                "############",
                "#.....#C...#",
                "############"
        ));
        Level level = parser.parseMap(map);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());


        Optional<Direction> next = clyde.nextAiMove();
        assertThat(Optional.empty()).isEqualTo(next);
    }
}