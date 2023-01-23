package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertSame;

public class BoardTest {

    private MapParser parser;
    private Level level;
    private Board board;
    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        PointCalculator pointCalculator = new DefaultPointCalculator();
        GhostFactory ghostFactory = new GhostFactory(sprites);
        BoardFactory boardFactory = new BoardFactory(sprites);
        LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory, pointCalculator);
        parser = new MapParser(levelFactory, boardFactory);
        ArrayList<String> map = new ArrayList<>(Lists.newArrayList( "#####", "#...#", "#...#", "#####" ));
        level = parser.parseMap(map); board = level.getBoard();
    }

    @ParameterizedTest(name = "x={0}, y={1}, result={2}")
    @CsvSource({
            "0, 0, true", "-3, -2, false","3,4,false",
            "4,3,true", "5,2, false", "2,3, true"
    })
    void testBoard(int x,int y, boolean result)
    {
        assertSame(result,board.withinBorders(x,y));
    }

}
