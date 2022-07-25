package ekosykh.edu.bacteria.swing;

import ekosykh.edu.bacteria.logic.Environment;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

public class BacteriaPanel extends JPanel {

    private final Environment environment;

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
        for(var bacteria: environment.getBacteriaSet()) {
            comp2D.setColor(Color.BLUE);
            var circle = new Ellipse2D.Float(
                    bacteria.getX(), bacteria.getY(), bacteria.getW(), bacteria.getH());
            comp2D.fill(circle);
        }
    }
}
