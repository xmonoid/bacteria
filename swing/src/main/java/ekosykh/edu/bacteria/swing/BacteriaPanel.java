package ekosykh.edu.bacteria.swing;

import ekosykh.edu.bacteria.logic.Bacteria;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class BacteriaPanel extends JPanel {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    private final List<Bacteria> bacteriaList = new ArrayList<>();

    public BacteriaPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.WHITE);
    }

    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    public void addBacteria() {
        bacteriaList.add( new Bacteria() );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D comp2D = (Graphics2D) g;
        comp2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(var bacteria: bacteriaList) {
            comp2D.setColor(bacteria.getColor());
            var circle = new Ellipse2D.Float(
                    bacteria.getX(), bacteria.getY(), bacteria.getW(), bacteria.getH());
            comp2D.fill(circle);
        }
    }
}
