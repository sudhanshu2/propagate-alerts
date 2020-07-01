package visualizer;

import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * This displays a "map" of compute nodes and sensors, and changes color as the data is forwarded
 *
 * @author sudhanshu2 / sudhanshu@isi.edu
 */
public class PropagateAlerts {
    private static JFrame mainFrame;

    private PropagateAlerts() {
        /* Prevents class from being instantiated */
    }

    /**
     * Main method for the application
     *
     * @param args command line arguments, disregarded in the current program
     */
    public static void main(String[] args) {
        mainFrame = new JFrame();
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setSize(600, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);

        new UbuntuMono();


    }


}
