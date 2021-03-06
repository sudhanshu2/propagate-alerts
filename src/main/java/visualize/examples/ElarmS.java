package visualize.examples;

import visualize.api.*;

import java.util.*;

public class ElarmS implements Runnable {
    private final double THRESHOLD = 2.5;
    private final long SPS;
    private final double ALPHA; /* (for 100 sps data a =0.99, for 20 sps data  a=0.95 */
    private final double MULTIPLIER;
    private final Random RANDOM;
    private int nodeID;

    private double xi;
    private double di;
    private double currentLoc;
    private double maxTp;

    public ElarmS(double minLocation, double maxLocation, int nodeID) {
        MULTIPLIER = minLocation + (maxLocation - minLocation);
        RANDOM = new Random();

        xi = -1;
        di = -1;
        maxTp = -1;
        currentLoc = -1;
        SPS = 100;
        this.nodeID = nodeID;
        ALPHA = SPS == 100? 0.99 : 0.95; /* (for 100 sps visualize.api.data a =0.99, for 20 sps visualize.api.data  a=0.95 */
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    private double getTp(double location) {
        if (xi == -1 || di == -1 || currentLoc == -1) {
            currentLoc = location;
            xi = 0;
            di = 0;
        }

        xi = calculateXi(location);
        di = calculateDi(location);

        double currentTp = 2 * Math.PI * Math.sqrt(xi / di);
        maxTp = Math.max(maxTp, currentTp);

        return currentTp ;
    }

    private double calculateXi(double location) {
        return ALPHA * xi + Math.pow(location, 2.0);
    }

    private double calculateDi(double location) {
        /* (for 100 sps data a =0.99, for 20 sps data  a=0.95 */
        return ALPHA * di + Math.pow((location - currentLoc) * SPS, 2.0);
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        FileHandling fileHandling = new FileHandling("csv/" + nodeID + ".csv");
        fileHandling.wipeCSV();
        System.out.println("started node " + nodeID);
        while (true) {
            double newLocation = MULTIPLIER * RANDOM.nextDouble();
            double newTp = getTp(newLocation);

            if (newTp > THRESHOLD
                    && newTp != Double.POSITIVE_INFINITY
                    && newTp != Double.NEGATIVE_INFINITY) {
                Propagation.propagateAlert(nodeID, true);
            }

            fileHandling.addToCSV(true, Double.toString(newTp));

            try {
                Thread.sleep(1000 / SPS);
            } catch (Exception e) {
                System.out.println("timer not working");
            }
        }
    }
}
