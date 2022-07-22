package ekosykh.edu.bacteria.swing;

import javax.swing.JFrame;
import java.awt.FlowLayout;

public class BacteriaFrame extends JFrame {

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1200;

    public BacteriaFrame() {
        super("bacteria");
        setLayout(new FlowLayout());
        setBounds(WIDTH/4, HEIGHT/4, WIDTH/2, HEIGHT/2);
        var bacteriaPanel = new BacteriaPanel();
        add(bacteriaPanel);
        add(new ControlPanel(this, bacteriaPanel));
        pack();
    }

}
