package ekosykh.edu.bacteria.swing;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

public class ControlPanel extends JPanel {

    public static final int WIDTH = 200;
    public static final int HEIGHT = 600;

    public ControlPanel(BacteriaFrame bacteriaFrame, BacteriaPanel bacteriaPanel) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        var bacteriaButton = new AddNewBacteriaButton(bacteriaFrame, bacteriaPanel);
        add(bacteriaButton);
    }

    public Dimension getPreferredSize() {
        return new Dimension(WIDTH,HEIGHT);
    }
}
