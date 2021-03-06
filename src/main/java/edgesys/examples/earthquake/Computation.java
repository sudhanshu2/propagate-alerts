package edgesys.examples.earthquake;

import com.twitter.heron.api.topology.*;
import com.twitter.heron.api.tuple.*;
import edgesys.api.*;

public class Computation extends NodeBolt {

    private final double THRESHOLD = 2.5;

    /*
     * Alpha value should be 0.99 for SPS = 100, and 0.95 for 20 SPS,
     *
     */
    private long SPS;
    private double ALPHA;
    private String fieldName;

    private double xi;
    private double di;
    private double currentLoc;
    private double maxTp;

    @Override
    public void initialize() {
        xi = -1;
        di = -1;
        maxTp = -1;
        currentLoc = -1;
        SPS = 100;
        ALPHA = 0.99;
        this.fieldName = "ground movement";
    }

    @Override
    public String getFieldName() {
        return fieldName;
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

    @Override
    public boolean computation(Object newLocation) {
        if (newLocation instanceof Double) {
            double newTp = getTp((Double) newLocation);

            if (newTp > THRESHOLD
                    && newTp != Double.POSITIVE_INFINITY
                    && newTp != Double.NEGATIVE_INFINITY) {
                /* Propagation */
            }
        } else {
            System.out.println("invalid input");
        }
        return false;
    }

    private double calculateXi(double location) {
        return ALPHA * xi + Math.pow(location, 2.0);
    }

    private double calculateDi(double location) {
        /* (for 100 sps data a =0.99, for 20 sps data  a=0.95 */
        return ALPHA * di + Math.pow((location - currentLoc) * SPS, 2.0);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sensor"));
    }
}
