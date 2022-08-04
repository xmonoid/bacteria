package ekosykh.edu.bacteria.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import static ekosykh.edu.bacteria.logic.Position.*;

class Bacteria extends TimerTask {

    private static final Random RND = new Random();
    private static final int MAX_X = Environment.WIDTH;
    private static final int MAX_Y = Environment.HEIGHT;
    private static final AtomicInteger nextId = new AtomicInteger(0);

    private int x;
    private int y;
    private final int id;
    private final Environment environment;
    private boolean alive;

    {
        this.id = nextId.incrementAndGet();
        alive = true;
    }

    public Bacteria(final Environment environment, int startX, int startY) {
        this.environment = environment;
        if (startX < 0 || startX >= MAX_X) {
            throw new IllegalArgumentException(
                    "Start x coordinate for a new bacteria must be between " + 0 + " " + MAX_X + ".");
        }
        if (startY < 0 || startY >= MAX_Y) {
            throw new IllegalArgumentException(
                    "Start y coordinate for a new bacteria must be between " + 0 + " " + MAX_Y + ".");
        }
        synchronized (environment.area) {
            if (environment.area[startX][startY] != EMPTY) {
                throw new BacteriaCannotBeCreatedException(
                        "The specified place has already been occupied: [" + startX + "][" + startY + "]");
            }
            this.x = startX;
            this.y = startY;
            environment.area[x][y] = BACTERIA_IS_HERE;
        }
    }

    public Bacteria(final Environment environment) {
        this.environment = environment;
        synchronized (environment.area) {
            setRandomFreePosition();
            environment.area[x][y] = BACTERIA_IS_HERE;
        }
    }

    private void setRandomFreePosition() {
        int i = 0;
        for (; i < 20; i++) {
            this.x = RND.nextInt(MAX_X);
            this.y = RND.nextInt(MAX_Y);
            if (environment.area[x][y] == EMPTY) {
                break;
            }
        }
        if (i == 20) { // 20 attempts to find randomly a free position failed
            int j;
            int k = 0;
            foundFreePosition:
            for (j = 0; j < environment.area.length; j++) {
                for (k = 0; k < environment.area[j].length; k++) {
                    if (environment.area[j][k] == EMPTY) {
                        // set the first free position in the area
                        this.x = j;
                        this.y = k;
                        break foundFreePosition;
                    }
                }
            }
            if (j == MAX_X && k == MAX_Y) {
                throw new BacteriaCannotBeCreatedException(
                        "There is no free position in the area.");
            }
        }
    }

    @Override
    public void run() {
        makeStep();
    }

    private void makeStep() {
        synchronized (environment.area) {
            // 1. Determine is there an available place for the next step
            var availableDirections = availableDirections();

            // 2. If there is no place, the bacteria dies
            if (availableDirections.isEmpty()) {
                alive = false;
                environment.area[x][y] = BACTERIA_IS_DEAD;
                this.cancel();
            } else {
                // Clean old position
                environment.area[x][y] = BACTERIA_WAS_HERE;

                // 3. Choose a random direction from the list of available ones
                int nextInt = RND.nextInt(availableDirections.size());
                var direction = availableDirections.get(nextInt);
                x += direction.getX();
                y += direction.getY();

                // Set new position
                environment.area[x][y] = BACTERIA_IS_HERE;
            }
        }
    }

    private List<Direction> availableDirections() {
        var result  = new ArrayList<Direction>(8);
        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                // Exclude walls and the current position
                if (i<0 || i>=MAX_X || j<0 || j>=MAX_Y || (i==x && j==y)) {
                    continue;
                }
                if (environment.area[i][j] == EMPTY) {
                    result.add( Direction.valueOf(i-x, j-y) );
                }
            }
        }
        return result;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
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