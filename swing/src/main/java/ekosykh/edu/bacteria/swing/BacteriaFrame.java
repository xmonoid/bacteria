package ekosykh.edu.bacteria.swing;

import ekosykh.edu.bacteria.logic.Environment;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.FlowLayout;

public class BacteriaFrame extends JFrame {

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1200;

    public BacteriaFrame() {
        super("bacteria");
        setLayout(new FlowLayout());
        setBounds(WIDTH/4, HEIGHT/4, WIDTH/2, HEIGHT/2);
        final var environment = new Environment();
        final var bacteriaPanel = new BacteriaPanel(environment);
        add(bacteriaPanel);
        final var controlPanel = new ControlPanel(environment, bacteriaPanel);
        add(controlPanel);
        pack();
        new Timer(100, e -> {
            bacteriaPanel.repaint();
            controlPanel.recountAliveBacteria();
        }).start();
    }
}
