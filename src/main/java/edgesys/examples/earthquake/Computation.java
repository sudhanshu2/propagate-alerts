package edgesys.examples.earthquake;

import com.twitter.heron.api.bolt.*;
import com.twitter.heron.api.topology.*;
import com.twitter.heron.api.tuple.*;
import edgesys.api.*;

public class Computation extends NodeBolt {

    private final double THRESHOLD = 2.5;
    private final long SPS;
    private final double ALPHA; /* (for 100 sps data a =0.99, for 20 sps data  a=0.95 */
    private int nodeID;

    private double xi;
    private double di;
    private double currentLoc;
    private double maxTp;

    public Computation(double minLocation, double maxLocation, int nodeID) {
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

        return currentTp;
    }

    private double calculateXi(double location) {
        return ALPHA * xi + Math.pow(location, 2.0);
    }

    private double calculateDi(double location) {
        /* (for 100 sps data a =0.99, for 20 sps data  a=0.95 */
        return ALPHA * di + Math.pow((location - currentLoc) * SPS, 2.0);
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        double newLoc = Double.parseDouble(input.getString(0));
        System.out.println(newLoc);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sensor"));
    }
}
