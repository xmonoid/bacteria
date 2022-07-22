package ekosykh.edu.bacteria.swing;

import javax.swing.JLabel;

public class BacteriaCountLabel extends JLabel {
    private final String capture;
    private int count;

    public BacteriaCountLabel(String capture) {
        super(capture + "0");
        this.capture = capture;
        count = 0;
    }

    public void addBacteria() {
        this.setText(capture + ++count);
    }
}
