package ekosykh.edu.bacteria.logic;

import java.util.Arrays;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import static ekosykh.edu.bacteria.logic.Position.EMPTY;

public class Environment {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    // this is the area where all bacteria live
    public final Position[][] area;
    private final Map<Integer, Bacteria> createdBacteria;
    private final Diffusion diffusion;
    private volatile int bacteriaDivisionTime;

    public Environment() {
        area = new Position[WIDTH][HEIGHT];
        Arrays.stream(area).forEach(a -> Arrays.fill(a, EMPTY));
        diffusion = new Diffusion(area);
        bacteriaDivisionTime = 10;
        createdBacteria = new ConcurrentHashMap<>();
        new Timer("Diffusion").scheduleAtFixedRate(diffusion, 0L, 1_000L);
    }

    public void addBacteria() {
        addBacteria(new Bacteria(this));
    }

    public void addBacteria(Bacteria bacteria) {
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

    public int getBacteriaDivisionTime() {
        return bacteriaDivisionTime;
    }

    public void setBacteriaDivisionTime(int bacteriaDivisionTime) {
        this.bacteriaDivisionTime = bacteriaDivisionTime;
    }

    public void cleanTracks() {
        synchronized (area) {
            for (int i = 0; i < area.length; i++) {
                for (int j = 0; j < area[i].length; j++) {
                    if (area[i][j] == Position.BACTERIA_WAS_HERE) {
                        area[i][j] = Position.EMPTY;
                    }
                }
            }
        }
    }
}
