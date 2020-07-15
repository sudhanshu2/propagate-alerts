package api;

import examples.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Map {
    private static NodeMap map;
    private static String fileName;
    private static ArrayList<Node> nodeArr;
    private static final int DISTANCE_THRESHOLD = 50;

    private Map() {
        /* to prevent instantiation */
    }

    protected static void initializeMap() {
        map = new NodeMap();
        nodeArr = new ArrayList<>();

        map.addImage("images/california-apple-maps.png");
        Map.fileName = "csv/nodes.csv";

        FileHandling.createNodeCSV(30, 10, 20);
        getNodes(Map.fileName);

        for (Node node : nodeArr) {
            map.add(node);
            node.startComputation();
        }

        PropagateAlerts.addPanel(map);
    }

    private static void getNodes(String fileName) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 2) {
                    System.out.println("need at least location of nodes\n");
                    System.exit(-1);
                }
                nodeArr.add(new Node(new ElarmS(Integer.parseInt(data[2]), Integer.parseInt(data[3])),
                        Integer.parseInt(data[0]), Integer.parseInt(data[1])));
            }

            int maxLatitude = Integer.MIN_VALUE;
            int maxLongitude = Integer.MIN_VALUE;

            for (Node node : nodeArr) {
                maxLatitude = Math.max(maxLatitude, node.getLatitude());
                maxLongitude = Math.max(maxLongitude, node.getLongitude());
            }

            double latitudeMultiplier = (double) map.getPreferredSize().height / maxLatitude;
            double longitudeMultiplier = (double) map.getPreferredSize().width / maxLongitude;

            for (Node node : nodeArr) {
                node.setBounds(node.getLongitude() * (int) latitudeMultiplier,
                        node.getLatitude() * (int) longitudeMultiplier,
                        node.getDiameter(), node.getDiameter());

                ArrayList<Integer> adjacency = new ArrayList<>();
                for (Node otherNode : nodeArr) {
                    if ((node.getDistance(otherNode.getLongitude(),
                            otherNode.getLatitude()) < DISTANCE_THRESHOLD) &&
                            !otherNode.equals(node)) {
                        adjacency.add(otherNode.getNodeID() - 1);
                    }
                }
                node.setAdjacency(adjacency);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("could not find file with list of nodes\n");
            System.exit(-1);
        }
    }

    public static ArrayList<Node> getNodeArr() {
        return nodeArr;
    }

    private static class NodeMap extends JPanel {
        private String backgroundFilePath;
        private static int gridSize;

        public NodeMap() {
            super();
            gridSize = 10;
            backgroundFilePath = "";
            setLayout(null);
        }

        protected void addImage(String backgroundFilePath) {
            this.backgroundFilePath = backgroundFilePath;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 600);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                Image backgroundImage = ImageIO.read(new File(backgroundFilePath));
                g.drawImage(backgroundImage, 0, 0, this);
            } catch (Exception e) {
                super.paintComponent(g);
                setBackground(new Color(237, 231, 246));
                System.out.println("could not load map background, using background color\n");
            }

            g.setColor(Color.LIGHT_GRAY);

            int cellLength = getHeight() / gridSize;
            int currentLocation = 0;

            for (int i = 0; i < gridSize + 1; i++) {
                g.drawLine(0, currentLocation, getWidth(), currentLocation);
                g.drawLine(currentLocation, 0, currentLocation, getHeight());
                currentLocation = currentLocation + cellLength;
            }
        }
    }
}
