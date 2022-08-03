package ekosykh.edu.bacteria.logic;

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

    static Direction valueOf(int x, int y) {
        switch (x) {
            case 0:
                switch (y) {
                    case -1:
                        return TOP;
                    case 1:
                        return BOTTOM;
                }
                break;
            case 1:
                switch (y) {
                    case -1:
                        return TOP_RIGHT;
                    case 0:
                        return RIGHT;
                    case 1:
                        return BOTTOM_RIGHT;
                }
                break;
            case -1:
                switch (y) {
                    case -1:
                        return TOP_LEFT;
                    case 0:
                        return LEFT;
                    case 1:
                        return BOTTOM_LEFT;
                }
                break;
        }
        throw new IllegalArgumentException("A coordinate may be changed only to -1, 0, or 1");
    }
}
