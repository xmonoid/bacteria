package ekosykh.edu.bacteria.logic;

import java.util.Arrays;

enum Direction {
    TOP_LEFT(-1, -1),
    TOP(0, -1),
    TOP_RIGHT(1, -1),
    RIGHT(1, 0),
    BOTTOM_RIGHT(1, 1),
    BOTTOM(0, 1),
    BOTTOM_LEFT(-1, 1),
    LEFT(-1, 0);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    static Direction getDirection(int x, int y) {
        return Arrays.stream(Direction.values())
                .filter(d -> d.x == x && d.y == y).findFirst().orElseThrow();
    }
}
