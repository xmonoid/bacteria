package ekosykh.edu.bacteria.logic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DissimilationTest {

    private static final int LENGTH = 5;

    @Test
    void testRun() {
        Position[][] startArea = new Position[LENGTH][LENGTH];
        var dissimilation = new Dissimilation(startArea);
        dissimilation.setTimeToDissimilate(0);

        for (int i = 0; i < LENGTH; i++) {
            if (i + 1 < LENGTH) {
                startArea[i+1][i] = Position.BACTERIA_WAS_HERE;
                startArea[i][i] = Position.BACTERIA_IS_DEAD;
                startArea[i][i+1] = Position.BACTERIA_IS_HERE;
            }
        }

        Position[][] endArea = Arrays.stream(startArea).map(Position[]::clone).toArray(Position[][]::new);
        for (int i = 0; i < LENGTH-1; i++) {
            endArea[i][i] = Position.EMPTY;
        }

        dissimilation.run();

        assertArrayEquals(endArea, startArea);
    }

}