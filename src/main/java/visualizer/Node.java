package visualizer;

import javax.swing.JLabel;
import java.awt.Component;

/**
 * Represents a node on the map
 **/
public class Node extends JLabel {
    Node(String string) {
        super(string);
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    Node(String string, UbuntuMono.FontStyle fontStyle, float fontSize) {
        this(string);
        setFont(fontStyle, fontSize);
    }

    void setFont(UbuntuMono.FontStyle fontStyle, float fontSize) {
        super.setFont(UbuntuMono.getFont(fontStyle, fontSize));
    }
}
