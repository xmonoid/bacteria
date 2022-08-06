package ekosykh.edu.bacteria.swing;

import ekosykh.edu.bacteria.logic.Environment;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class BacteriaPanel extends JPanel {

    private static final Color ALIVE_BACTERIA_COLOR = new Color(0x22B14C);
    private static final Color GAS_TRACK_COLOR = new Color(0xFF7F27);

    private final Environment environment;

    private boolean trackMovements = true;
    private boolean showDead = true;

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
                    switch (environment.area[i][j]) {
                        case BACTERIA_IS_DEAD:
                            if (showDead) {
                                comp2D.setColor(Color.BLACK);
                                comp2D.fillOval(i, j, 3, 3);
                            }
                            break;
                        case BACTERIA_IS_HERE:
                            comp2D.setColor(ALIVE_BACTERIA_COLOR);
                            comp2D.fillOval(i, j, 3, 3);
                            break;
                        case BACTERIA_WAS_HERE:
                            if (trackMovements) {
                                comp2D.setColor(GAS_TRACK_COLOR);
                                comp2D.fillOval(i, j, 1, 1);
                            }
                            break;
                    }
                }
            }
        }
    }

    public void setTrackMovements(boolean trackMovements) {
        this.trackMovements = trackMovements;
    }

    public void setShowDead(boolean showDead) {
        this.showDead = showDead;
    }
}
