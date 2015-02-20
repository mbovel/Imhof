package ch.epfl.imhof;

public final class PointGeo {

    private final double longitude;
    private final double latitude;

    static private final double LONGITUDE_LOWER_BOUND = -java.lang.Math.PI;
    static private final double LONGITUDE_UPPER_BOUND = java.lang.Math.PI;
    static private final double LATITUDE_LOWER_BOUND = -java.lang.Math.PI / 2;
    static private final double LATITUDE_UPPER_BOUND = java.lang.Math.PI / 2;

    public PointGeo(double longitude, double latitude) {
        if (longitude < LONGITUDE_LOWER_BOUND) {
            throw new IllegalArgumentException("longitude should be at least "
                    + LONGITUDE_LOWER_BOUND);
        }
        else if (longitude > LONGITUDE_UPPER_BOUND) {
            throw new IllegalArgumentException("longitude should be at most "
                    + LONGITUDE_UPPER_BOUND);
        }
        else if (latitude < LATITUDE_LOWER_BOUND) {
            throw new IllegalArgumentException("longitude should be at least "
                    + LATITUDE_LOWER_BOUND);
        }
        else if (latitude > LATITUDE_UPPER_BOUND) {
            throw new IllegalArgumentException("longitude should be at most "
                    + LATITUDE_UPPER_BOUND);
        }

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double longitude() {
        return longitude;
    }

    public double latitude() {
        return latitude;
    }

}
