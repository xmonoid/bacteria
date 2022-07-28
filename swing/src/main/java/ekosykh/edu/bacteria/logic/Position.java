package ekosykh.edu.bacteria.logic;

enum Position {
    EMPTY(0),
    BACTERIA_IS_HERE(1),
    BACTERIA_WAS_HERE(2);

    private final int value;

    Position(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
