package ekosykh.edu.bacteria.logic;

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
    private final int[][] area;

    public Bacteria(final int[][] area) {
        this.x = RND.nextInt(MAX_X);
        this.y = RND.nextInt(MAX_Y);
        this.id = nextId++;
        this.area = area;
    }

    @Override
    public void run() {
        makeStep();
    }

    private void makeStep() {
        // Clean old position
        synchronized (area) {
            area[x][y] = Position.BACTERIA_WAS_HERE.getValue();
        }

        int stepOnX;
        switch (x) {
            case 0: // the bacteria is at the right wall
                stepOnX = RND.nextInt(2); // 0 or 1
                break;
            case MAX_X-1: // the bacteria is at the right wall
                stepOnX = RND.nextInt(2) - 1; // -1 or 0
                break;
            default:
                stepOnX = RND.nextInt(3) - 1; // -1, 0, or 1
        }
        x += stepOnX;

        int stepOnY;
        switch (y) {
            case 0: // the bacteria is at the top wall
                stepOnY = RND.nextInt(2); // 0 or 1
                break;
            case MAX_Y-1: // the bacteria is at the bottom wall
                stepOnY = RND.nextInt(2) - 1; // -1 or 0
                break;
            default:
                stepOnY = RND.nextInt(3) - 1; // -1, 0, or 1
        }
        y += stepOnY;

        // Set new position
        synchronized (area) {
            area[x][y] = Position.BACTERIA_IS_HERE.getValue();
        }
    }

    public int getId() {
        return id;
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