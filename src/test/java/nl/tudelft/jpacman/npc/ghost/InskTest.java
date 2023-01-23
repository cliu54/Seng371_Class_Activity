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


public class InskTest {

    private GhostMapParser parser;
    private Player pacman;

    @BeforeEach

    public void setUp() {
        PacManSprites sprites = new PacManSprites();
        PointCalculator pointCalculator = new DefaultPointCalculator();
        GhostFactory ghostFactory = new GhostFactory(sprites);
        BoardFactory boardFactory = new BoardFactory(sprites);
        LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory, pointCalculator);
        parser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        PlayerFactory playerFactory = new PlayerFactory(sprites);
        pacman = playerFactory.createPacMan();
    }

    @Test
    void testNotMove()
    {
        ArrayList<String> map = new ArrayList<>(List.of(
                "############",
                "#B.I....P..#",
                "############"
        ));
        Level level = parser.parseMap(map);

        Inky inky = Navigation.findUnitInBoard(Inky.class,level.getBoard());
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.WEST);
        Optional<Direction> next = inky.nextAiMove();
        assertFalse((next.isPresent()));
    }

    @Test
    void testMoveCloser()
    {
        ArrayList<String> map = new ArrayList<>(List.of(
                "############",
                "#B.P......I#",
                "############"

        ));
        Level level = parser.parseMap(map);

        Inky inky = Navigation.findUnitInBoard(Inky.class,level.getBoard());
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.EAST);

        Optional<Direction> next = inky.nextAiMove();
        assertTrue(next.isPresent());
        assertSame(Direction.WEST,next.get());

    }

    @Test
    void testMoveAway()
    {
        ArrayList<String> map = new ArrayList<>(List.of(
                "############",
                "#.P.B.I....#",
                "############"

        ));
        Level level = parser.parseMap(map);

        Inky inky = Navigation.findUnitInBoard(Inky.class,level.getBoard());
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.WEST);
        Optional<Direction> next = inky.nextAiMove();
        assertTrue(next.isPresent());
        assertSame(Direction.EAST,next.get());


    }


}
