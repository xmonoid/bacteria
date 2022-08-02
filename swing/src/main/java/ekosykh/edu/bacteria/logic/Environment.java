package ekosykh.edu.bacteria.logic;

import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public class Environment {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    // this is the area where all bacteria live
    public final int[][] area;
    private final Map<Integer, Bacteria> createdBacteria;
    private final Diffusion diffusion;

    public Environment() {
        area = new int[WIDTH][HEIGHT];
        diffusion = new Diffusion(area);
        createdBacteria = new ConcurrentHashMap<>();
        new Timer("Diffusion").scheduleAtFixedRate(diffusion, 0L, 1_000L);
    }

    public void addBacteria() {
        var bacteria = new Bacteria(this);
        var bacteriaTimer = new Timer("Bacteria #" + bacteria.getId());
        bacteriaTimer.scheduleAtFixedRate(bacteria, 0L, 1_000L);
        createdBacteria.put(bacteria.getId(), bacteria);
    }

    public int getBacteriaNumber() {
        return createdBacteria.size();
    }

    public int getAliveBacteriaNumber() {
        return (int) createdBacteria.entrySet().stream().filter(
                bacteria -> bacteria.getValue().isAlive()
        ).count();
    }

    public int getGasTrailLength() {
        return diffusion.getGasTrailLength();
    }

    public void setGasTrailLength(int gasTrailLength) {
        diffusion.setGasTrailLength(gasTrailLength);
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
