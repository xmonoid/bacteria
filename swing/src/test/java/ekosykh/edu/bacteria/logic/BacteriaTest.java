package ekosykh.edu.bacteria.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BacteriaTest {

    @Test
    void testMakeOnePossibleStep() {
        var environment = new Environment();
        environment.area[10][10] = Position.BACTERIA_IS_HERE.getValue();
        var bacteria = new Bacteria(environment, 10, 10);

        environment.area[9][9] = environment.area[10][9] = environment.area[11][9] =
                environment.area[11][10] = environment.area[11][11] = environment.area[10][11] =
                        environment.area[9][11] = Position.BACTERIA_WAS_HERE.getValue();

        bacteria.run();

        assertTrue( bacteria.isAlive() );
        assertEquals( 9, bacteria.getX());
        assertEquals( 10, bacteria.getY());
        assertEquals( Position.BACTERIA_IS_HERE.getValue(), environment.area[9][10] );
        assertEquals( Position.BACTERIA_WAS_HERE.getValue(), environment.area[10][10] );
    }

    @Test
    void testMakeOnePossibleStepInTheCorner() {
        var environment = new Environment();
        environment.area[0][0] = Position.BACTERIA_IS_HERE.getValue();
        var bacteria = new Bacteria(environment, 0, 0);

        environment.area[0][1] = environment.area[1][0] = Position.BACTERIA_WAS_HERE.getValue();

        bacteria.run();

        assertTrue( bacteria.isAlive() );
        assertEquals( 1, bacteria.getX());
        assertEquals( 1, bacteria.getY());
        assertEquals( Position.BACTERIA_IS_HERE.getValue(), environment.area[1][1] );
        assertEquals( Position.BACTERIA_WAS_HERE.getValue(), environment.area[0][0] );
    }

    @Test
    void testDie() {
        var environment = new Environment();
        environment.area[10][10] = Position.BACTERIA_IS_HERE.getValue();
        var bacteria = new Bacteria(environment, 10, 10);

        environment.area[9][9] = environment.area[10][9] = environment.area[11][9] =
                environment.area[11][10] = environment.area[11][11] = environment.area[10][11] =
                        environment.area[9][11] = environment.area[9][10] = Position.BACTERIA_WAS_HERE.getValue();

        bacteria.run();

        assertFalse( bacteria.isAlive() );
        assertEquals( 10, bacteria.getX());
        assertEquals( 10, bacteria.getY());
        assertEquals( Position.BACTERIA_IS_DEAD.getValue(), environment.area[10][10] );
    }
}