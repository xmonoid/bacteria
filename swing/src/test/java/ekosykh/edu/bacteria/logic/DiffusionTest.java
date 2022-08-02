package ekosykh.edu.bacteria.logic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DiffusionTest {

    private static final int LENGTH = 5;

    @Test
    void testSubtractAreas() {
        int[][] minuend = new int[LENGTH][LENGTH];

        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                minuend[i][j] = i + j;
            }
        }
        int[][] subtrahend = Arrays.stream(minuend).map(int[]::clone).toArray(int[][]::new);
        assertArrayEquals(minuend, subtrahend);

        Diffusion.subtractAreas(minuend, subtrahend);
        int[][] zeros = new int[LENGTH][LENGTH];
        assertArrayEquals(minuend, zeros);
    }

    @Test
    void testRun() {
        int[][] startArea = new int[LENGTH][LENGTH];
        var diffusion = new Diffusion(startArea);
        diffusion.setGasTrailLength(0);

        for (int i = 0; i < LENGTH; i++) {
            if (i+1 < LENGTH) {
                startArea[i][i] = Position.BACTERIA_WAS_HERE.getValue();
                startArea[i][i+1] = Position.BACTERIA_IS_HERE.getValue();
            }
        }

        int[][] endArea = Arrays.stream(startArea).map(int[]::clone).toArray(int[][]::new);
        for (int i = 0; i < LENGTH-1; i++) {
            endArea[i][i] = Position.EMPTY.getValue();
        }

        diffusion.run();

        assertArrayEquals(endArea, startArea);
    }
}