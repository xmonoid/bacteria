package ekosykh.edu.bacteria.swing;

import ekosykh.edu.bacteria.logic.Environment;

import javax.swing.JLabel;

public class BacteriaAliveCountLabel extends JLabel {
    private final String capture;
    private final Environment environment;

    public BacteriaAliveCountLabel(final String capture,
                                   final Environment environment) {
        super(capture + environment.getAliveBacteriaNumber());
        this.capture = capture;
        this.environment = environment;
    }

    public void recountBacteria() {
        this.setText(capture + environment.getAliveBacteriaNumber());
    }
}
