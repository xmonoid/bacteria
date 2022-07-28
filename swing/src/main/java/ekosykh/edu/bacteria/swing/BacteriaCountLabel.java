package ekosykh.edu.bacteria.swing;

import ekosykh.edu.bacteria.logic.Environment;

import javax.swing.JLabel;

public class BacteriaCountLabel extends JLabel {
    private final String capture;

    private final Environment environment;

    public BacteriaCountLabel(final String capture,
                              final Environment environment) {
        super(capture + environment.getBacteriaNumber());
        this.environment = environment;
        this.capture = capture;
    }

    public void recountBacteria() {
        this.setText(capture + environment.getBacteriaNumber());
    }
}
