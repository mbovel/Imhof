package ch.epfl.imhof.painting;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.Test;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.dem.HGTDigitalElevationModel;
import ch.epfl.imhof.geometry.Vector3d;

public class OurHGTDigitalElevationModelTest {
    @Test
    public void alpineTerrainExempleWorks() throws Exception {
        double n = 46.2;
        double s = 46.8;
        double w = 7.2;
        double e = 7.8;
        
        double realWidth = s - n;
        double realHeight = e - w;
        int imgWidth = 800;
        int imgHeight = 800;
        int[] out = new int[imgWidth * imgHeight];
        
        File demFile = new File("test/data/big/N46E007.hgt");
        HGTDigitalElevationModel dem = new HGTDigitalElevationModel(demFile);
        
        BufferedImage imageOut = new BufferedImage(
            imgWidth,
            imgHeight,
            BufferedImage.TYPE_INT_RGB);
        
        for (int i = 0; i != imgWidth; ++i) {
            for (int j = 0; j != imgHeight; ++j) {
                double lat = w + ((double)i / imgWidth) * realWidth;
                double lon = n + ((double)j / imgHeight) * realHeight;
                PointGeo point = new PointGeo(
                    Math.toRadians(lat),
                    Math.toRadians(lon));
                Vector3d normal = dem.normalAt(point).normalized();
                float grey = (float) (0.5 * (normal.y() + 1));
                Color color = new Color(grey, grey, grey);
                out[i * imgWidth + j] = color.getRGB();
            }
        }
        
        dem.close();
        
        imageOut.setRGB(0, 0, imgWidth, imgHeight, out, 0, imgWidth);
        
        ImageIO.write(imageOut, "png", new File("test.png"));
    }
}
