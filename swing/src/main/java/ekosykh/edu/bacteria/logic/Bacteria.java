package ekosykh.edu.bacteria.logic;

import ekosykh.edu.bacteria.swing.BacteriaPanel;

import java.util.Random;

public class Bacteria {
    private static final Random RND = new Random();
    private static final int WIDTH = 3;
    private static final int HEIGHT = 3;
    private static final int MAX_X = Environment.WIDTH;
    private static final int MAX_Y = Environment.HEIGHT;
    private static int nextId = 0;

    private int x;
    private int y;
    private final int id;

    public Bacteria() {
        this.x = RND.nextInt(MAX_X);
        this.y = RND.nextInt(MAX_Y);
        this.id = nextId++;
    }

    public void makeStep() {
        int stepOnX;
        switch (x) {
            case 0: // the bacteria is at the right wall
                stepOnX = RND.nextInt(2); // 0 or 1
                break;
            case MAX_X: // the bacteria is at the right wall
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
            case MAX_Y: // the bacteria is at the bottom wall
                stepOnY = RND.nextInt(2) - 1; // -1 or 0
                break;
            default:
                stepOnY = RND.nextInt(3) - 1; // -1, 0, or 1
        }
        y += stepOnY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return WIDTH;
    }

    public int getH() {
        return HEIGHT;
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