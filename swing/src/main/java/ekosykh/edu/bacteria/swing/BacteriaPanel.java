package ekosykh.edu.bacteria.swing;

import ekosykh.edu.bacteria.logic.Bacteria;
import ekosykh.edu.bacteria.logic.Environment;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class BacteriaPanel extends JPanel {

    private final Environment environment;

    private boolean trackMovements = true;

    public BacteriaPanel(final Environment environment) {
        this.environment = environment;
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.WHITE);
    }

    public Dimension getPreferredSize() {
        return new Dimension(Environment.WIDTH, Environment.HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D comp2D = (Graphics2D) g;
        comp2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        synchronized (environment.area) {
            for (int i = 0; i < environment.area.length; i++) {
                for (int j = 0; j < environment.area[i].length; j++) {
                    if (environment.area[i][j] > 0) {
                        switch (environment.area[i][j]) {
                            case 1:
                                comp2D.setColor(Color.BLUE);
                                comp2D.fillOval(i, j, Bacteria.WIDTH, Bacteria.HEIGHT);
                                break;
                            case 2:
                                if (trackMovements) {
                                    comp2D.setColor(Color.RED);
                                    comp2D.fillOval(i, j, 1, 1);
                                }
                                break;
                        }
                    }
                }
            }
        }
    }

    public void setTrackMovements(boolean trackMovements) {
        this.trackMovements = trackMovements;
    }
}
