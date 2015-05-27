package ch.epfl.imhof;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.xml.sax.SAXException;

import ch.epfl.imhof.dem.Earth;
import ch.epfl.imhof.dem.HGTDigitalElevationModel;
import ch.epfl.imhof.dem.ReliefShader;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.Vector3d;
import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMMapReader;
import ch.epfl.imhof.osm.OSMToGeoTransformer;
import ch.epfl.imhof.painting.Color;
import ch.epfl.imhof.painting.Java2DCanvas;
import ch.epfl.imhof.painting.SwissPainter;
import ch.epfl.imhof.projection.CH1903Projection;

/**
 * The main program.
 * 
 * @see {@link #main} for possible arguments
 * 
 * @author Matteo Besançon (245826)
 */
public final class Main {
    private static CH1903Projection projection = new CH1903Projection();
    
    /**
     * Prints a map of a part of Switzerland, given the following arguments
     * (separated by spaces in command line) are:
     * <ol>
     * <li>path of the gziped OSM File,
     * <li>path of the HGT file,
     * <li>longitude of the bottom left point of the map (in degrees),
     * <li>latitude of the bottom left point of the map (in degrees),
     * <li>longitude of the top right point of the map (in degrees),
     * <li>latitude of the top right point of the map (in degrees),
     * <li>resolution of the output (in dpi),
     * <li>path of the output PNG file.
     * 
     * @see <a href="http://cs108.epfl.ch/p00_intro.html">Introduction to the
     *      project (french)</p>
     * @param args
     *            an array of arguments, see detailed description before
     * @throws IOException
     *             if there is a problem when trying to read or write a file
     *             (either OSM, HGT or the output file)
     * @throws SAXException
     *             if there is a problem when parsing the OSM XML file
     */
    public static void main(String[] args) throws IOException, SAXException {
        String osmFile = args[0];
        String hgtFile = args[1];
        PointGeo bl = new PointGeo(
            Math.toRadians(Double.parseDouble(args[2])),
            Math.toRadians(Double.parseDouble(args[3])));
        PointGeo tr = new PointGeo(
            Math.toRadians(Double.parseDouble(args[4])),
            Math.toRadians(Double.parseDouble(args[5])));
        int resolution = Integer.parseInt(args[6]);
        String outputFile = args[7];
        
        // Get bottom left and top right points in CH1903 coordinate system
        Point blProjected = projection.project(bl);
        Point trProjected = projection.project(tr);
        
        // Find final image width and height
        int height = (int) Math.round(resolution * 39.3701 / 25_000
                * (tr.latitude() - bl.latitude()) * Earth.RADIUS);
        int width = (int) Math.round((trProjected.x() - blProjected.x())
                / (trProjected.y() - blProjected.y()) * height);
        
        // Read OSM File and paint it
        Map map = osmGzFileToMap(osmFile);
        
        Java2DCanvas canvas = new Java2DCanvas(
            blProjected,
            trProjected,
            width,
            height,
            resolution,
            Color.WHITE);
        
        SwissPainter.painter().drawMap(map, canvas);
        
        // Read HGT file and paint it
        ReliefShader shader = new ReliefShader(
            projection,
            new HGTDigitalElevationModel(new File(hgtFile)),
            new Vector3d(-1.0, 1.0, 1.0));
        
        BufferedImage shade = shader.shadedRelief(
            trProjected,
            blProjected,
            width,
            height,
            resolution / 25.41 * 1.7);
        
        // Merge images made from OSM and HGT files
        BufferedImage finalImage = merge(canvas.image(), shade, width, height);
        
        // Output it to the given path
        ImageIO.write(finalImage, "png", new File(outputFile));
    }
    
    private static Map osmGzFileToMap(String osmFile) throws IOException,
            SAXException {
        OSMMap osmMap = OSMMapReader.readOSMFile(osmFile, true);
        return new OSMToGeoTransformer(projection).transform(osmMap);
    }
    
    private static BufferedImage merge(BufferedImage a, BufferedImage b,
            int width, int height) {
        BufferedImage finalImage = new BufferedImage(
            width,
            height,
            BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y != height; ++y) {
            for (int x = 0; x != width; ++x) {
                // getRGB returns a color in the RGBA color model by default. As
                // we need RGB, we need to subtract 0xFF000000.
                Color aColor = Color.rgb(a.getRGB(x, y) - 0xFF000000);
                Color bColor = Color.rgb(b.getRGB(x, y) - 0xFF000000);
                
                finalImage.setRGB(x, y, aColor
                    .multiply(bColor)
                    .toJavaColor()
                    .getRGB());
            }
        }
        
        return finalImage;
    }
    
}
