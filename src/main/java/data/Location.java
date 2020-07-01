package data;

public class Location {
    private double latitude;
    private double longitude;

    Location() {
        this(-1, -1);
    }

    Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double distanceFrom(double toLatitude, double toLongitude) {
        return Math.sqrt(Math.pow(latitude - toLatitude, 2.0) + Math.pow(longitude - toLongitude, 2.0));
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
