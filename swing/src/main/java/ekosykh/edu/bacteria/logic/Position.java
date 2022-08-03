package ekosykh.edu.bacteria.logic;

import java.util.NoSuchElementException;

public enum Position {
    EMPTY(0),
    BACTERIA_IS_HERE(1),
    BACTERIA_WAS_HERE(2),
    BACTERIA_IS_DEAD(-1);

    private final int value;

    Position(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Position valueOf(int value) {
        switch (value) {
            case 0: return EMPTY;
            case 1: return BACTERIA_IS_HERE;
            case 2: return BACTERIA_WAS_HERE;
            case -1: return BACTERIA_IS_DEAD;
            default: throw new IllegalArgumentException("Enum Position has no value " + value);
        }
    }
}
