package ekosykh.edu.bacteria.swing;

import ekosykh.edu.bacteria.logic.Environment;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Color;
import java.awt.Dimension;
import java.util.stream.IntStream;

public class ControlPanel extends JPanel {

    public static final int WIDTH = 200;
    public static final int HEIGHT = 600;

    private final BacteriaAliveCountLabel aliveCount;
    private final BacteriaTotalCountLabel bacteriaCount;

    public ControlPanel(final Environment environment,
                        final BacteriaPanel bacteriaPanel) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        var bacteriaButton = new JButton("Add 1 new Bacteria");
        add(bacteriaButton);
        var oneHundredBacteriaButton = new JButton("Add 100 new Bacteria");
        add(oneHundredBacteriaButton);
        var oneThousandBacteriaButton = new JButton("Add 1000 new Bacteria");
        add(oneThousandBacteriaButton);
        bacteriaCount = new BacteriaTotalCountLabel("Total bacteria: ", environment);
        add(bacteriaCount);
        aliveCount = new BacteriaAliveCountLabel("Alive bacteria:", environment);
        add(aliveCount);
        var trackCheckBox = new JCheckBox("Show bacteria tracks");
        trackCheckBox.setSelected(true);
        add(trackCheckBox);
        var gasTrailLabel = new JLabel("Tracks length: ");
        add(gasTrailLabel);
        var gasTrailSpinner = new JSpinner(new SpinnerNumberModel(environment.getGasTrailLength(),
                0, Integer.MAX_VALUE, 1));
        add(gasTrailSpinner);
        var cleanTracksButton = new JButton("Clean tracks");
        add(cleanTracksButton);

        trackCheckBox.addActionListener(event ->
                bacteriaPanel.setTrackMovements(trackCheckBox.isSelected())
        );
        bacteriaButton.addActionListener(event -> new Thread(environment::addBacteria).start());
        oneHundredBacteriaButton.addActionListener(event -> new Thread(() -> IntStream.range(0, 100)
                .parallel()
                .forEach(i -> environment.addBacteria())).start());
        oneThousandBacteriaButton.addActionListener(event -> new Thread(() -> IntStream.range(0, 1000)
                .parallel()
                .forEach(i -> environment.addBacteria())).start());
        gasTrailSpinner.addChangeListener( event -> {
            Object value = gasTrailSpinner.getValue();
            if (value instanceof Integer) {
                environment.setGasTrailLength((Integer) value);
            }
        } );
        cleanTracksButton.addActionListener(event -> environment.cleanTracks());
    }

    void recountAliveBacteria() {
        bacteriaCount.recountBacteria();
        aliveCount.recountBacteria();
    }

    public Dimension getPreferredSize() {
        return new Dimension(WIDTH,HEIGHT);
    }
}
