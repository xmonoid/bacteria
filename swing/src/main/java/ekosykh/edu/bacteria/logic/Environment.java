package ekosykh.edu.bacteria.logic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;

public class Environment {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    // this is the area where all bacteria live
    public final int[][] area = new int[WIDTH][HEIGHT];
    private final Set<Bacteria> bacteriaSet = new HashSet<>();

    public void addBacteria() {
        var bacteria = new Bacteria(area);
        bacteriaSet.add(bacteria);
        new Timer("Bacteria #" + bacteria.getId()).
                scheduleAtFixedRate(bacteria, 0L, 1_000L);
    }

    public Set<Bacteria> getBacteriaSet() {
        return Collections.unmodifiableSet(bacteriaSet);
    }
}
