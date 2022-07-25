package ekosykh.edu.bacteria.swing;

import ekosykh.edu.bacteria.logic.Environment;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

public class ControlPanel extends JPanel {

    public static final int WIDTH = 200;
    public static final int HEIGHT = 600;

    public ControlPanel(final BacteriaFrame bacteriaFrame,
                        final Environment environment) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        var bacteriaButton = new JButton("Add new Bacteria");
        add(bacteriaButton);
        var bacteriaCount = new BacteriaCountLabel("Bacteria count: ", environment);
        add(bacteriaCount);

        bacteriaButton.addActionListener(event -> {
            environment.addBacteria();
            bacteriaCount.recountBacteria();
            bacteriaFrame.repaint();
        });
    }

    public Dimension getPreferredSize() {
        return new Dimension(WIDTH,HEIGHT);
    }
}
