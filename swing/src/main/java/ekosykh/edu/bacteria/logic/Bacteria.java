package ekosykh.edu.bacteria.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private int numberOfSteps;

    {
        this.id = nextId.incrementAndGet();
        alive = true;
    }

    public Bacteria(final Environment environment, int startX, int startY) {
        this.environment = environment;
        if (startX < 0 || startX >= MAX_X) {
            throw new IllegalArgumentException(
                    "Start x coordinate is illegal -> " + startX);
        }
        if (startY < 0 || startY >= MAX_Y) {
            throw new IllegalArgumentException(
                    "Start y coordinate is illegal -> " + startY);
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
        synchronized (environment.area) {
            // Determine is there an available place for the next action
            final var nextPlace = nextDirection();

            // If there is no place, the bacteria dies
            if (nextPlace.isEmpty()) {
                alive = false;
                environment.area[x][y] = BACTERIA_IS_DEAD;
                this.cancel();
            } else {
                if (numberOfSteps < environment.getBacteriaDivisionTime()) {
                    // The bacteria make one more step
                    makeStep(nextPlace.get());
                    numberOfSteps++;
                } else {
                    // The bacteria spawn a new one
                    environment.addBacteria(spawnNewOne(nextPlace.get()));
                    numberOfSteps = 0;
                }
            }
        }
    }

    private void makeStep(Direction direction) {
        // Clean old position
        environment.area[x][y] = BACTERIA_WAS_HERE;

        x += direction.getX();
        y += direction.getY();

        // Set new position
        environment.area[x][y] = BACTERIA_IS_HERE;
    }

    private Bacteria spawnNewOne(Direction direction) {
        var x1 = x + direction.getX();
        var y1 = y + direction.getY();
        return new Bacteria(environment, x1, y1);
    }

    private Optional<Direction> nextDirection() {
        // Choose a random direction from the list of available ones
        var directions = availableDirections();
        if (directions.isEmpty()) {
            return Optional.empty();
        }
        int nextInt = RND.nextInt(directions.size());
        return Optional.of(directions.get(nextInt));
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