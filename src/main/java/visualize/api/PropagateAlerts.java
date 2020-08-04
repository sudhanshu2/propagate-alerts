package visualize.api;

import javax.swing.JFrame;
import java.awt.Component;
import java.awt.GridBagLayout;

/**
 * This displays a "map" of compute nodes and sensors, and changes color as the visualize.api.data is forwarded
 *
 * @author sudhanshu2 / sudhanshu@isi.edu
 */
public class PropagateAlerts {
    private static JFrame mainFrame;

    private PropagateAlerts() {
        /* Prevents class from being instantiated */
    }

    /**
     * This method is called from other classes in order to add a JPanel
     * to the JFrame mainFrame in this class.
     * @param panel The JPanel you are adding the the JFrame.
     */
    static void addPanel(Component panel) {
        mainFrame.add(panel);
        mainFrame.validate();
        mainFrame.repaint();
    }

    /**
     * This method revalidates the mainFrame, helps in case of custom layout add functions are used
     */
    static void revalidateMain() {
        mainFrame.validate();
        mainFrame.repaint();
    }

    /**
     * Removes all components from the mainFrame
     */
    static void clear() {
        mainFrame.getContentPane().removeAll();
        mainFrame.validate();
        mainFrame.repaint();
    }

    /**
     * Main method for the application
     *
     * @param args command line arguments, disregarded in the current program
     */
    public static void main(String[] args) {
        mainFrame = new JFrame();
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setSize(625, 650);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);

        UbuntuMono.initializeUbuntuMono();

        Map.initializeMap();

    }
}
