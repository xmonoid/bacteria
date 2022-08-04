package ekosykh.edu.bacteria.logic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DiffusionTest {

    private static final int LENGTH = 5;

    @Test
    void testRun() {
        Position[][] startArea = new Position[LENGTH][LENGTH];
        var diffusion = new Diffusion(startArea);
        diffusion.setGasTrailLength(0);

        for (int i = 0; i < LENGTH; i++) {
            if (i+1 < LENGTH) {
                startArea[i][i] = Position.BACTERIA_WAS_HERE;
                startArea[i][i+1] = Position.BACTERIA_IS_HERE;
            }
        }

        Position[][] endArea = Arrays.stream(startArea).map(Position[]::clone).toArray(Position[][]::new);
        for (int i = 0; i < LENGTH-1; i++) {
            endArea[i][i] = Position.EMPTY;
        }

        diffusion.run();

        assertArrayEquals(endArea, startArea);
    }
}