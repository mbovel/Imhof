package ch.epfl.imhof.dem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel.MapMode;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Vector3d;

/**
 * Represents a digital elevation model in the NASA .hgt format.
 * 
 * @see <a href="http://wiki.openstreetmap.org/wiki/SRTM">More informations
 *      about HGT format (see section Format)</a>
 * @author Matthieu Bovel (250300)
 */
public final class HGTDigitalElevationModel implements DigitalElevationModel {
    private final PointGeo        southWest;
    private final int             rowLength;
    private final double          resolution;
    private final double          s;
    private final FileInputStream stream;
    private final ShortBuffer     buffer;
    private final static double   DELTA          = 0.0000001;
    private final static double   HGT_FILE_WIDTH = Math.toRadians(1.0);
    
    /**
     * Construct a digital elevation model given an hgt file.
     * 
     * @param file
     *            the hgt file of the digital elevation model
     * 
     * @throws IllegalArgumentException
     *             when the .hgt file is not conform (ie. not correctly named or
     *             is not square)
     * @throws IOException
     *             if some I/O error occurs
     */
    public HGTDigitalElevationModel(File file) throws IllegalArgumentException,
            IOException {
        String fileName = file.getName();
        
        // 1. Get point at the bottom left (south west)
        
        double lat = Double.parseDouble(fileName.substring(1, 3));
        double lon = Double.parseDouble(fileName.substring(4, 7));
        
        switch (fileName.substring(0, 1)) {
            case "S":
                lat *= -1;
                break;
            case "N":
                // wonderful, lat is positive, do nothing
                break;
            default:
                throw new IllegalArgumentException(
                    "first character in file name must be either 'S' or 'N'");
        }
        
        switch (fileName.substring(3, 4)) {
            case "W":
                lon *= -1;
                break;
            case "E":
                // wonderful, lon is positive, do nothing
                break;
            default:
                throw new IllegalArgumentException(
                    "fourth character in file name must be either 'W' or 'E'");
        }
        
        southWest = new PointGeo(Math.toRadians(lon), Math.toRadians(lat));
        
        // 2. Find resolution
        
        long length = file.length();
        double n = Math.sqrt(length / 2);
        
        if (Math.floor(n) != n) {
            throw new IllegalArgumentException(
                "illegal file format (file length must be a square)");
        }
        
        rowLength = (int) n;
        resolution = HGT_FILE_WIDTH / (rowLength - 1);
        s = resolution * Earth.RADIUS;
        
        // 3. Buffer file
        
        stream = new FileInputStream(file);
        
        buffer = stream
            .getChannel()
            .map(MapMode.READ_ONLY, 0, length)
            .asShortBuffer();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * ch.epfl.imhof.dem.DigitalElevationModel#normalAt(ch.epfl.imhof.PointGeo)
     */
    @Override
    public Vector3d normalAt(PointGeo point) {
        int i = pointGeoToIndex(point);
        
        // Top left (z_i,j):
        short tl = buffer.get(i);
        
        // Top right (z_i,j+1):
        short tr = buffer.get(i + 1);
        
        // Bottom left (z_i+1,j):
        short bl = buffer.get(i + rowLength);
        
        // Bottom right (z_i+1,j+1):
        short br = buffer.get(i + rowLength + 1);
        
        // For readability:
        
        // Vector3d a = new Vector3d(s, 0, sw - nw);
        // Vector3d b = new Vector3d(0, s, ne - nw);
        // Vector3d c = new Vector3d(-s, 0, ne - se);
        // Vector3d d = new Vector3d(0, -s, sw - se);
        //
        // Vector3d n1 = a.cross(b);
        // Vector3d n2 = c.cross(d);
        //
        // return n1.add(n2).multiply(0.5);
        
        // For performance:
        
        return new Vector3d(0.5 * s * (bl - br + tl - tr), 0.5 * s
                * (bl + br - tl - tr), Math.pow(s, 2.0));
    }
    
    // Package visibility for testing (see OurHGTDigitalElevationModelTest)
    int pointGeoToIndex(PointGeo p) {
        // The floorDelta method is used instead of (int)Math.floor so that edge
        // cases give correct results (most left bottom point in a HTD file for
        // example). It does however not change anything in most cases. See
        // OurHGTDigitalElevationModelTest for more details.
        int column = floorDelta((p.longitude() - southWest.longitude())
                / resolution);
        int row = floorDelta(rowLength - 1.0
                - (p.latitude() - southWest.latitude()) / resolution);
        
        if (row < 0) {
            throw new IllegalArgumentException("point's too far north");
        }
        if (column < 0) {
            throw new IllegalArgumentException("point's too far west");
        }
        if (row >= rowLength) {
            throw new IllegalArgumentException("point's too far south");
        }
        if (column >= rowLength) {
            throw new IllegalArgumentException("point's too far east");
        }
        
        return row * rowLength + column;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.AutoCloseable#close()
     */
    @Override
    public void close() throws Exception {
        stream.close();
    }
    
    private static int floorDelta(double n) {
        double nCeiled = Math.ceil(n);
        
        if (nCeiled - n < DELTA) {
            return (int) nCeiled;
        }
        
        return (int) Math.floor(n);
    }
}
