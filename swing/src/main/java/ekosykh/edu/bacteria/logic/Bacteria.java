package ekosykh.edu.bacteria.logic;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

class Bacteria extends TimerTask {

    private static final Random RND = new Random();
    private static final int MAX_X = Environment.WIDTH;
    private static final int MAX_Y = Environment.HEIGHT;
    private static int nextId = 0;

    private int x;
    private int y;
    private final int id;
    private final Environment environment;
    private boolean alive;

    public Bacteria(final Environment environment) {
        this.x = RND.nextInt(MAX_X);
        this.y = RND.nextInt(MAX_Y);
        this.id = nextId++;
        this.environment = environment;
        alive = true;
    }

    Bacteria(final Environment environment, int startX, int startY) {
        this(environment);
        this.x = startX;
        this.y = startY;
    }

    @Override
    public void run() {
        makeStep();
    }

    void makeStep() {
        // 1. Determine is there an available place for the next step
        var availableDirections = new ArrayList<Direction>(8);
        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                // Exclude walls and the current position
                if (i<0 || i>=MAX_X || j<0 || j>=MAX_Y || (i==x && j==y)) {
                    continue;
                }
                if (environment.area[i][j] == 0) {
                    availableDirections.add( Direction.getDirection(i-x, j-y) );
                }
            }
        }
        // 2. If there is no place, the bacteria dies
        if (availableDirections.isEmpty()) {
            this.cancel();
            alive = false;
        } else {
            // Clean old position
            synchronized (environment.area) {
                environment.area[x][y] = Position.BACTERIA_WAS_HERE.getValue();
            }

            // 3. Choose a random direction from the list of available ones
            int nextInt = RND.nextInt(availableDirections.size());
            var direction = availableDirections.get(nextInt);
            x += direction.getX();
            y += direction.getY();

            // Set new position
            synchronized (environment.area) {
                environment.area[x][y] = Position.BACTERIA_IS_HERE.getValue();
            }
        }
    }

    public int getId() {
        return id;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bacteria bacteria = (Bacteria) o;

        return id == bacteria.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}