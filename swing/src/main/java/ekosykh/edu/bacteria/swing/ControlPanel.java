package ekosykh.edu.bacteria.swing;

import ekosykh.edu.bacteria.logic.Environment;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

public class ControlPanel extends JPanel {

    public static final int WIDTH = 200;
    public static final int HEIGHT = 600;

    private final BacteriaAliveCountLabel aliveCount;

    public ControlPanel(final Environment environment,
                        final BacteriaPanel bacteriaPanel) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        var bacteriaButton = new JButton("Add new Bacteria");
        add(bacteriaButton);
        var bacteriaCount = new BacteriaTotalCountLabel("Total bacteria: ", environment);
        add(bacteriaCount);
        aliveCount = new BacteriaAliveCountLabel("Alive bacteria:", environment);
        add(aliveCount);
        var trackCheckBox = new JCheckBox("Show bacteria tracks");
        trackCheckBox.setSelected(true);
        add(trackCheckBox);
        var freshTracksButton = new JButton("Fresh tracks");
        add(freshTracksButton);

        trackCheckBox.addActionListener(event ->
                bacteriaPanel.setTrackMovements(trackCheckBox.isSelected())
        );
        bacteriaButton.addActionListener(event -> {
            environment.addBacteria();
            bacteriaCount.recountBacteria();
            aliveCount.recountBacteria();
        });
        freshTracksButton.addActionListener(event -> environment.cleanTracks());
    }

    void recountAliveBacteria() {
        aliveCount.recountBacteria();
    }

    public Dimension getPreferredSize() {
        return new Dimension(WIDTH,HEIGHT);
    }
}
