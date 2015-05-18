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
        double n = 46.8;
        double s = 46.2;
        double w = 7.2;
        double e = 7.8;
        
        double realWidth = n - s;
        double realHeight = e - w;
        int imgWidth = 800;
        int imgHeight = 800;
        int[] out = new int[imgWidth * imgHeight];
        
        File demFile = new File("test/data/big/N46E007.hgt");
        HGTDigitalElevationModel dem = new HGTDigitalElevationModel(demFile);
        
        BufferedImage imageOut = new BufferedImage(imgWidth, imgHeight,
                BufferedImage.TYPE_INT_RGB);
        
        for (int i = 0; i != imgHeight; ++i) {
            double lat = n - ((double) i / imgHeight) * realHeight;
            for (int j = 0; j != imgWidth; ++j) {
                double lon = w + ((double) j / imgWidth) * realWidth;
                PointGeo point = new PointGeo(Math.toRadians(lon),
                        Math.toRadians(lat));
                Vector3d normal = dem.normalAt(point).normalized();
                float grey = (float) (1 - 0.5 * (normal.x() + 1));
                // float grey = (float) (0.5 * (normal.y() + 1));
                Color color = new Color(grey, grey, grey);
                out[i * imgWidth + j] = color.getRGB();
            }
        }
        
        dem.close();
        
        imageOut.setRGB(0, 0, imgWidth, imgHeight, out, 0, imgWidth);
        
        ImageIO.write(imageOut, "png", new File("test.png"));
    }
}
