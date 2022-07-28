package ekosykh.edu.bacteria.logic;

import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public class Environment {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    // this is the area where all bacteria live
    public final int[][] area = new int[WIDTH][HEIGHT];
    private final Map<Integer, Timer> createdBacteria = new ConcurrentHashMap<>();

    public void addBacteria() {
        var bacteria = new Bacteria(area);
        var bacteriaTimer = new Timer("Bacteria #" + bacteria.getId());
        bacteriaTimer.scheduleAtFixedRate(bacteria, 0L, 1_000L);
        createdBacteria.put(bacteria.getId(), bacteriaTimer);
    }

    public int getBacteriaNumber() {
        return createdBacteria.size();
    }

    public void cleanTracks() {
        synchronized (area) {
            for (int i = 0; i < area.length; i++) {
                for (int j = 0; j < area[i].length; j++) {
                    if (area[i][j] == Position.BACTERIA_WAS_HERE.getValue()) {
                        area[i][j] = Position.EMPTY.getValue();
                    }
                }
            }
        }
    }
}
