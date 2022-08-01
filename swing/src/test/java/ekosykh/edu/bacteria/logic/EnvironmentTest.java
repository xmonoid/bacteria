package ekosykh.edu.bacteria.logic;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EnvironmentTest {

    @Test
    void testAlive() throws NoSuchFieldException, IllegalAccessException {
        var environment = new Environment();

        var b1 = new Bacteria(environment);
        var b2 = new Bacteria(environment);

        var bacteriaMap = Map.of(
                b1.getId(), b1,
                b2.getId(), b2
        );

        Field createdBacteria = environment.getClass().getDeclaredField("createdBacteria");
        createdBacteria.setAccessible(true);
        createdBacteria.set(environment, bacteriaMap);

        assertEquals( 2, environment.getBacteriaNumber() );
        assertEquals( 2, environment.getAliveBacteriaNumber() );

        Field alive = b1.getClass().getDeclaredField("alive");
        alive.setAccessible(true);
        alive.setBoolean(b1, false);

        assertEquals( 2, environment.getBacteriaNumber() );
        assertEquals( 1, environment.getAliveBacteriaNumber() );
    }

    @Test
    void testCleanTracks() {
        var environment = new Environment();
        environment.area[100][100] = Position.BACTERIA_WAS_HERE.getValue();
        environment.cleanTracks();
        Arrays.stream(environment.area).flatMapToInt( Arrays::stream ).forEach( it ->
                assertNotEquals( Position.BACTERIA_WAS_HERE.getValue(), it ));
    }
}