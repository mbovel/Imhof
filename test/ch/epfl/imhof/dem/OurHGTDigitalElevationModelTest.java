package ch.epfl.imhof.dem;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.Test;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Vector3d;
import ch.epfl.imhof.testUtils.OurTestsUtils;

public class OurHGTDigitalElevationModelTest {
    @Test
    public void alpineTerrainExempleWorks() throws Exception {
        double n = 46.8;
        double s = 46.2;
        double w = 7.2;
        double e = 7.8;
        
        double realHeight = n - s;
        double realWidth = e - w;
        int imgWidth = 800;
        int imgHeight = 800;
        
        File demFile = new File("test/data/big/N46E007.hgt");
        HGTDigitalElevationModel dem = new HGTDigitalElevationModel(demFile);
        
        BufferedImage imageOut = new BufferedImage(
            imgWidth,
            imgHeight,
            BufferedImage.TYPE_INT_RGB);
        
        double heightRatio = realHeight / imgHeight;
        double widthRatio = realWidth / imgWidth;
        
        for (int y = 0; y != imgHeight; ++y) {
            double lat = Math.toRadians(n - y * heightRatio);
            
            for (int x = 0; x != imgWidth; ++x) {
                double lon = Math.toRadians(w + x * widthRatio);
                
                PointGeo point = new PointGeo(lon, lat);
                Vector3d normal = dem.normalAt(point).normalized();
                
                float grey = (float) (0.5 * (normal.y() + 1));
                Color color = new Color(grey, grey, grey);
                imageOut.setRGB(x, y, color.getRGB());
            }
        }
        
        dem.close();
        
        OurTestsUtils.assertImagesSame("test/data/shaded-orientation.png", imageOut);
    }
    
    @Test
    public void topLeftPoint() throws Exception {
        File demFile = new File("test/data/big/N46E007.hgt");
        HGTDigitalElevationModel dem = new HGTDigitalElevationModel(demFile);
        
        int i = dem.pointGeoToIndex(new PointGeo(Math.toRadians(7), Math
            .toRadians(47)));
        
        assertEquals(0, i);
        
        dem.close();
    }
    
    @Test
    public void topRightPoint() throws Exception {
        File demFile = new File("test/data/big/N46E007.hgt");
        HGTDigitalElevationModel dem = new HGTDigitalElevationModel(demFile);
        
        int i = dem.pointGeoToIndex(new PointGeo(Math.toRadians(8), Math
            .toRadians(47)));
        
        assertEquals(rowLength(demFile) - 1, i);
        
        dem.close();
    }
    
    @Test
    public void bottomLeftPoint() throws Exception {
        File demFile = new File("test/data/big/N46E007.hgt");
        HGTDigitalElevationModel dem = new HGTDigitalElevationModel(demFile);
        
        int i = dem.pointGeoToIndex(new PointGeo(Math.toRadians(7), Math
            .toRadians(46)));
        
        assertEquals(demFile.length() / 2 - rowLength(demFile), i);
        
        dem.close();
    }
    
    @Test
    public void bottomRightPoint() throws Exception {
        File demFile = new File("test/data/big/N46E007.hgt");
        HGTDigitalElevationModel dem = new HGTDigitalElevationModel(demFile);
        
        int i = dem.pointGeoToIndex(new PointGeo(Math.toRadians(8), Math
            .toRadians(46)));
        
        assertEquals(demFile.length() / 2 - 1, i);
        
        dem.close();
    }
    
    @Test
    public void mostBottomRightPoint() throws Exception {
        File demFile = new File("test/data/big/N46E007.hgt");
        HGTDigitalElevationModel dem = new HGTDigitalElevationModel(demFile);
        
        int i = dem.pointGeoToIndex(new PointGeo(Math.toRadians(8.0002777777), Math
            .toRadians(45.9997222223)));
        
        assertEquals(demFile.length() / 2 - 1, i);
        
        dem.close();
    }
    
    @Test(expected = Exception.class)
    public void tooFarNorth() throws Exception {
        File demFile = new File("test/data/big/N46E007.hgt");
        HGTDigitalElevationModel dem = new HGTDigitalElevationModel(demFile);
        
        dem.pointGeoToIndex(new PointGeo(Math.toRadians(7), Math
            .toRadians(47.000001)));
        
        dem.close();
    }
    
    @Test(expected = Exception.class)
    public void tooFarWest() throws Exception {
        File demFile = new File("test/data/big/N46E007.hgt");
        HGTDigitalElevationModel dem = new HGTDigitalElevationModel(demFile);
        
        dem.pointGeoToIndex(new PointGeo(Math.toRadians(6.999999), Math
            .toRadians(47)));
        
        dem.close();
    }
    
    @Test(expected = Exception.class)
    public void tooFarSouth() throws Exception {
        File demFile = new File("test/data/big/N46E007.hgt");
        HGTDigitalElevationModel dem = new HGTDigitalElevationModel(demFile);
        
        dem.pointGeoToIndex(new PointGeo(Math.toRadians(8.0002777777), Math
            .toRadians(45.9997222222)));
        
        dem.close();
    }
    
    @Test(expected = Exception.class)
    public void tooFarEast() throws Exception {
        File demFile = new File("test/data/big/N46E007.hgt");
        HGTDigitalElevationModel dem = new HGTDigitalElevationModel(demFile);
        
        dem.pointGeoToIndex(new PointGeo(Math.toRadians(8.0002777778), Math
            .toRadians(45.9997222223)));
        
        dem.close();
    }
    
    private static int rowLength(File demFile) {
        return (int) Math.sqrt(demFile.length() / 2);
    }
}
