package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 *
 * @author Jeroen Roosen 
 *
 */
class OccupantTest {

    /**
     * The unit under test.
     */
    private Unit unit;
    private Square sq;

    /**
     * Resets the unit under test.
     */
    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
        sq = new BasicSquare();
    }

    /**
     * Asserts that a unit has no square to start with.
     */
    @Test
    void noStartSquare() {
        assertThat(unit.hasSquare()).isEqualTo(false);
    }

    /**
     * Tests that the unit indeed has the target square as its base after
     * occupation.
     */
    @Test
    void testOccupy() {
        // call occupy method and check existence of square
        unit.occupy(sq);
        assertThat(unit.getSquare().getOccupants()).isNotNull();
    }

    /**
     * Test that unit indeed successfully remove square
     */
    @Test
    void testleaveSquare(){
        // add and call leaveSquare() to delete
        unit.occupy(sq);
        unit.leaveSquare();

        // check existence of square
        assertThat(unit.hasSquare()).isFalse();
    }
    /**
     * Test that the unit indeed has the target square as its base after
     * double occupation.
     */
    @Test
    void testReoccupy() {
        // add, remove, add
        unit.occupy(sq);
        unit.leaveSquare();
        unit.occupy(sq);

        // check existence of square
        assertThat(unit.getSquare().getOccupants()).isNotNull();
    }
}
