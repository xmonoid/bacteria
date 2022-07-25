package ekosykh.edu.bacteria.logic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Environment {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    private final Set<Bacteria> bacteriaSet = new HashSet<>();

    public void addBacteria() {
        var bacteria = new Bacteria();
        bacteriaSet.add(bacteria);
        bacteria.start();
    }

    public Set<Bacteria> getBacteriaSet() {
        return Collections.unmodifiableSet(bacteriaSet);
    }
}
