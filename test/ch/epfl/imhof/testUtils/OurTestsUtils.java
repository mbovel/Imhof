package ch.epfl.imhof.testUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.xml.sax.SAXException;

import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMMapReader;

public class OurTestsUtils {
    private static double IMAGE_DIFF_DELTA = 0.005;
    
    public static OSMMap readOSMFile(final String fileName) throws IOException,
            SAXException {
        assumeFileExists(fileName);
        final boolean unGZip = fileExtension(fileName).equals("gz");
        return OSMMapReader.readOSMFile(fileName, unGZip);
    }
    
    private static void assumeFileExists(final String fileName) {
        // Conditionally ignoring tests in JUnit 4
        // http://stackoverflow.com/a/1689309
        // Class Assume
        // http://junit.sourceforge.net/javadoc/org/junit/Assume.html
        // How do I check if a file exists in Java?
        // http://stackoverflow.com/a/1816676
        assumeTrue(fileName + " exists", new File(fileName).isFile());
    }
    
    private static String fileExtension(final String fileName) {
        // How do I get the file extension of a file in Java?
        // http://stackoverflow.com/a/16202288
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
    
    // Percentage difference between images, rosettacode.org
    // http://rosettacode.org/wiki/Percentage_difference_between_images#Java
    static void assertImagesSame(String exceptedFileName, BufferedImage actual)
            throws IOException {
        assumeFileExists(exceptedFileName);
        
        BufferedImage excepted = ImageIO.read(new File(exceptedFileName));
        int width1 = excepted.getWidth(null);
        int width2 = actual.getWidth(null);
        int height1 = excepted.getHeight(null);
        int height2 = actual.getHeight(null);
        long diff = 0;
        
        if (width1 != width2 || height1 != height2) {
            System.err.println("Error: Images dimensions mismatch");
            System.exit(1);
        }
        
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                int rgb1 = excepted.getRGB(x, y);
                int rgb2 = actual.getRGB(x, y);
                int r1 = rgb1 >> 16 & 0xff;
                int g1 = rgb1 >> 8 & 0xff;
                int b1 = rgb1 & 0xff;
                int r2 = rgb2 >> 16 & 0xff;
                int g2 = rgb2 >> 8 & 0xff;
                int b2 = rgb2 & 0xff;
                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        }
        
        double n = width1 * height1 * 3;
        double p = diff / n / 255.0;
        
        assertEquals("check " + exceptedFileName
                + " is the same as actual image", 0.0, p, IMAGE_DIFF_DELTA);
    }
}
