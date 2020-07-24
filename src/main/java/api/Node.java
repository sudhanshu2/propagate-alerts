package api;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Represents a node on the map
 **/
public class Node extends JButton {
    private Clicked isClicked;
    private Trigger isTriggered;
    private final int DIAMETER;
    private int longitude;
    private int latitude;
    private final Runnable compute;
    private int nodeID;
    private Thread computation;

    protected Node(Runnable compute, int longitude, int latitude, int nodeID) {
        super();

        isClicked = Clicked.NOT_CLICKED;
        isTriggered = Trigger.IDLE;
        DIAMETER = 20;
        this.longitude = longitude;
        this.latitude = latitude;

        setAlignmentX(Component.CENTER_ALIGNMENT);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setPreferredSize(new Dimension(DIAMETER, DIAMETER));
        addActionListener(ae -> setIsClicked(Clicked.CLICKED));
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                setIsClicked(Clicked.HOVER);
            }

            public void mouseExited(MouseEvent evt) {
                setIsClicked(Clicked.NOT_CLICKED);
            }
        });

        this.compute = compute;
        this.nodeID = nodeID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Node otherNode = (Node) obj;
        return otherNode.getNodeID() == getNodeID();
    }

    public int getNodeID() {
        return nodeID;
    }

    protected void startComputation() {
        if (compute != null) {
            computation = new Thread(compute);
            computation.start();
        } else {
            System.out.println("invalid node created");
            System.exit(1);
        }
    }

    public void forwardAlert() {
        setIsTriggered(Trigger.FORWARDING);
    }

    public void startAlert() {
        setIsTriggered(Trigger.ORIGIN);
    }

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    protected int getDiameter() {
        return DIAMETER;
    }

    private void setIsClicked(Clicked isClicked) {
        this.isClicked = isClicked;
        repaint();
    }

    public void setIsTriggered(Trigger isTriggered) {
        this.isTriggered = isTriggered;
        repaint();
    }

    public int getDistance (int longitude, int latitude) {
        return (int) Math.sqrt(Math.pow(this.longitude - longitude, 2)
                + Math.pow(this.latitude - latitude, 2));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isTriggered == Trigger.FORWARDING) {
            g.setColor(new Color(245, 127, 23));
        } else if (isTriggered == Trigger.ORIGIN) {
            g.setColor(new Color(255, 0, 0));
        } else {
            g.setColor(new Color(0, 0, 255));
        }

        if (isClicked == Clicked.CLICKED) {
            g.setColor(new Color(g.getColor().getRed(), g.getColor().getGreen(), g.getColor().getBlue(), 255));
        } else if (isClicked  == Clicked.HOVER) {
            g.setColor(new Color(g.getColor().getRed(), g.getColor().getGreen(), g.getColor().getBlue(), 200));
        } else {
            g.setColor(new Color(g.getColor().getRed(), g.getColor().getGreen(), g.getColor().getBlue(), 175));
        }

        g.fillOval(0, 0, getPreferredSize().width, getPreferredSize().height);
    }

    public enum Trigger {
        IDLE, ORIGIN, FORWARDING
    }

    public enum Clicked {
        NOT_CLICKED, HOVER, CLICKED
    }
}
