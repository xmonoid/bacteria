package ekosykh.edu.bacteria.logic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static ekosykh.edu.bacteria.logic.Position.*;
import static org.junit.jupiter.api.Assertions.*;

class BacteriaTest {

    @Test
    void testCreateBacteria() {
        final var environment = new Environment();
        assertThrowsExactly(IllegalArgumentException.class, () ->
                new Bacteria(environment, Environment.WIDTH, 0));
        assertThrowsExactly(IllegalArgumentException.class, () ->
                new Bacteria(environment, 0, Environment.HEIGHT));
        environment.area[0][0] = BACTERIA_WAS_HERE;
        assertThrowsExactly(BacteriaCannotBeCreatedException.class, () ->
                new Bacteria(environment, 0, 0));
        Arrays.stream(environment.area).forEach(a -> Arrays.fill(a, BACTERIA_WAS_HERE));
        assertThrowsExactly(BacteriaCannotBeCreatedException.class, () ->
                new Bacteria(environment));
        environment.area[0][0] = EMPTY;
        assertDoesNotThrow(() -> {
            final var bacteria = new Bacteria(environment);
            assertEquals(0, bacteria.getX());
            assertEquals(0, bacteria.getY());
            assertEquals(BACTERIA_IS_HERE, environment.area[0][0]);
        });
    }

    @Test
    void testMakeOnePossibleStep() {
        var environment = new Environment();
        var bacteria = new Bacteria(environment, 10, 10);

        environment.area[9][9] = environment.area[10][9] = environment.area[11][9] =
                environment.area[11][10] = environment.area[11][11] = environment.area[10][11] =
                        environment.area[9][11] = BACTERIA_WAS_HERE;

        bacteria.run();

        assertTrue( bacteria.isAlive() );
        assertEquals( 9, bacteria.getX());
        assertEquals( 10, bacteria.getY());
        assertEquals( Position.BACTERIA_IS_HERE, environment.area[9][10] );
        assertEquals( Position.BACTERIA_WAS_HERE, environment.area[10][10] );
    }

    @Test
    void testMakeOnePossibleStepInTheCorner() {
        var environment = new Environment();
        var bacteria = new Bacteria(environment, 0, 0);

        environment.area[0][1] = environment.area[1][0] = Position.BACTERIA_WAS_HERE;

        bacteria.run();

        assertTrue( bacteria.isAlive() );
        assertEquals( 1, bacteria.getX());
        assertEquals( 1, bacteria.getY());
        assertEquals( Position.BACTERIA_IS_HERE, environment.area[1][1] );
        assertEquals( Position.BACTERIA_WAS_HERE, environment.area[0][0] );
    }

    @Test
    void testDie() {
        var environment = new Environment();
        var bacteria = new Bacteria(environment, 10, 10);

        environment.area[9][9] = environment.area[10][9] = environment.area[11][9] =
                environment.area[11][10] = environment.area[11][11] = environment.area[10][11] =
                        environment.area[9][11] = environment.area[9][10] = Position.BACTERIA_WAS_HERE;

        bacteria.run();

        assertFalse( bacteria.isAlive() );
        assertEquals( 10, bacteria.getX());
        assertEquals( 10, bacteria.getY());
        assertEquals( Position.BACTERIA_IS_DEAD, environment.area[10][10] );
    }
}