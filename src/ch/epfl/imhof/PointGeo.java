package ch.epfl.imhof;

/**
 * A point on earth, represented by its spherical coordinates (longitude and
 * latitude).
 * 
 * @author Matthieu Bovel (250300)
 * @author Matteo Besançon (245826)
 */
public final class PointGeo {

    private final double longitude;
    private final double latitude;

    static private final double LONGITUDE_LOWER_BOUND = -java.lang.Math.PI;
    static private final double LONGITUDE_UPPER_BOUND = java.lang.Math.PI;
    static private final double LATITUDE_LOWER_BOUND = -java.lang.Math.PI / 2;
    static private final double LATITUDE_UPPER_BOUND = java.lang.Math.PI / 2;

    /**
     * Construct a point, given its longitude and latitude
     * 
     * @param longitude
     *            point's longitude, in radians
     * @param latitude
     *            point's latitude, in radians
     * @throws IllegalArgumentException
     *             if longitude is smaller than -PI
     * @throws IllegalArgumentException
     *             if longitude is bigger than PI
     * @throws IllegalArgumentException
     *             if latitude is smaller than -PI/2
     * @throws IllegalArgumentException
     *             if latitude is bigger than PI/2
     */
    public PointGeo(double longitude, double latitude)
            throws IllegalArgumentException {
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

    /**
     * @return point's longitude
     */
    public double longitude() {
        return longitude;
    }

    /**
     * @return point's latitude
     */
    public double latitude() {
        return latitude;
    }

}
