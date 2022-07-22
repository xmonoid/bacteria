package ekosykh.edu.bacteria.swing;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class BacteriaPanel extends JPanel {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    private boolean isDraw = false;

    public BacteriaPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.WHITE);
    }

    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    private static final int w = 3;
    private static final int h = 3;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(! isDraw() ) return;
        Graphics2D comp2D = (Graphics2D) g;
        comp2D.setColor(Color.BLUE);
        comp2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        var random = new Random();
        int x = random.nextInt(WIDTH);
        int y = random.nextInt(HEIGHT);
        Ellipse2D.Float circle = new Ellipse2D.Float(x, y, w, h);
        comp2D.fill(circle);
    }

    boolean isDraw() {  return isDraw;  }

    void setDraw(boolean isDraw) {  this.isDraw = isDraw;   }
}
