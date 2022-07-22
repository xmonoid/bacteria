package ekosykh.edu.bacteria;

import ekosykh.edu.bacteria.swing.BacteriaFrame;

import javax.swing.JFrame;

public class BacteriaApplication {
    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(BacteriaApplication::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        var frame = new BacteriaFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
