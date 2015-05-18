package ch.epfl.imhof.dem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel.MapMode;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Vector3d;

public class HGTDigitalElevationModel implements DigitalElevationModel {
    private final PointGeo        southWest;
    private final int             rowLength;
    private final double          resolution;
    private final double          s;
    private final FileInputStream stream;
    private final ShortBuffer     buffer;
    
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
        resolution = Math.toRadians(1.0 / rowLength);
        s = resolution * Earth.RADIUS;
        
        // 3. Buffer file
        
        stream = new FileInputStream(file);
        
        buffer = stream
            .getChannel()
            .map(MapMode.READ_ONLY, 0, length)
            .asShortBuffer();
    }
    
    @Override
    public Vector3d normalAt(PointGeo point) {
        int column = (int) ((point.longitude() - southWest.longitude()) / resolution);
        int row = (int) (rowLength - ((point.latitude() - southWest.latitude()) / resolution));
        
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
        
        // South West (bottom left, z_i+1,j):
        short sw = buffer.get(row * rowLength + column);
        
        // South East (bottom right, z_i+1,j+1):
        short se = buffer.get(row * rowLength + column + 1);
        
        // Nord West (top left, z_i,j):
        short nw = buffer.get((row - 1) * rowLength + column);
        
        // Nord East (top right, z_i,j+1):
        short ne = buffer.get((row - 1) * rowLength + column + 1);
        
         Vector3d a = new Vector3d(s, 0, sw - nw);
         Vector3d b = new Vector3d(0, s, ne - nw);
         Vector3d c = new Vector3d(-s, 0, ne - se);
         Vector3d d = new Vector3d(0, -s, sw - se);
        
         Vector3d n1 = a.cross(b);
         Vector3d n2 = c.cross(d);
        
         return n1.add(n2).multiply(0.5);
        
        //return new Vector3d(0.5 * s * (nw - sw + ne - se), 0.5 * s
        //        * (nw + sw - ne - se), Math.pow(s, 2.0));
    }
    
    @Override
    public void close() throws Exception {
        stream.close();
    }
}
