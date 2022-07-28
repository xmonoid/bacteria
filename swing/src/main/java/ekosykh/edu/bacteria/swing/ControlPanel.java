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

    public ControlPanel(final Environment environment, final BacteriaPanel bacteriaPanel) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        var bacteriaButton = new JButton("Add new Bacteria");
        add(bacteriaButton);
        var bacteriaCount = new BacteriaCountLabel("Bacteria count: ", environment);
        add(bacteriaCount);
        var trackCheckBox = new JCheckBox("Track bacteria movements");
        trackCheckBox.setSelected(true);
        add(trackCheckBox);
        var cleanTracksButton = new JButton("Clean tracks");
        add(cleanTracksButton);

        trackCheckBox.addActionListener(event ->
                bacteriaPanel.setTrackMovements(trackCheckBox.isSelected())
        );
        bacteriaButton.addActionListener(event -> {
            environment.addBacteria();
            bacteriaCount.recountBacteria();
        });
        cleanTracksButton.addActionListener(event -> environment.cleanTracks());
    }

    public Dimension getPreferredSize() {
        return new Dimension(WIDTH,HEIGHT);
    }
}
