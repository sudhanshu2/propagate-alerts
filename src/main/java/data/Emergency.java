package data;

public class Emergency {
    private int latitude;
    private int longitude;
    private Severity severity;

    Emergency() {
       this(-1, -1, Severity.UNKNOWN);
    }

    Emergency(int latitude, int longitude) {
        this(0, 0, Severity.UNKNOWN);
    }

    Emergency(int latitude, int longitude, Severity severity) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.severity = severity;
    }


}
