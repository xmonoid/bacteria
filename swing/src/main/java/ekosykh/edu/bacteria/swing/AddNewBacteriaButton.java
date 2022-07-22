package ekosykh.edu.bacteria.swing;

import javax.swing.JButton;

public class AddNewBacteriaButton extends JButton {

    public AddNewBacteriaButton(BacteriaFrame bacteriaFrame, BacteriaPanel bacteriaPanel) {
        super("Add new Bacteria");
        this.addActionListener(event -> {
            bacteriaPanel.setDraw(true);
            bacteriaFrame.repaint();
        });
    }
}
