package computation;

public class ElarmS {
    private double xi = -1;
    private double di = -1;
    private double currentLoc = -1;
    private final double ALPHA = 0.99; /* (for 100 sps data a =0.99, for 20 sps data  a=0.95 */
    private double maxTp = -1;

    private ElarmS() {
        /* Private constructor to prevent instantiation */
    }

    public double getTp(double location) {
        if (xi == -1 || di == -1 || currentLoc == -1) {
            currentLoc = location;
            xi = 0;
            di = 0;
        }

        xi = calculateXi(location);
        di = calculateDi(location);

        double currentTp = 2 * Math.PI * Math.sqrt(xi / di);
        System.out.println(currentTp);
        maxTp = Math.max(maxTp, currentTp);

        return currentTp;
    }

    private double calculateXi(double location) {
        return ALPHA * xi + Math.pow(location, 2.0);
    }

    private double calculateDi(double location) {
        /* (for 100 sps data a =0.99, for 20 sps data  a=0.95 */
        double SPS = 100;
        return ALPHA * di + Math.pow((location - currentLoc) * SPS, 2.0);
    }
}
