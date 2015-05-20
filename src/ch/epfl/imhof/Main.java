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

public class Main {
    
    public static void main(String[] args) throws IOException, SAXException {
        String osmFile = args[0];
        String hgtFile = args[1];
        PointGeo bl = new PointGeo(Math.toRadians(Double.parseDouble(args[2])),
                Math.toRadians(Double.parseDouble(args[3])));
        PointGeo tr = new PointGeo(Math.toRadians(Double.parseDouble(args[4])),
                Math.toRadians(Double.parseDouble(args[5])));
        int res = Integer.parseInt(args[6]);
        String outputFile = args[7];
        
        CH1903Projection projection = new CH1903Projection();
        Point blProjected = projection.project(bl);
        System.out.println(blProjected.x());
        Point trProjected = projection.project(tr);
        System.out.println(trProjected.x());
        
        int height = (int) Math.round(((res / 0.0254) / 25_000)
                * (Math.toRadians(tr.latitude()) - Math.toRadians(bl.latitude()))
                * Earth.RADIUS);
        System.out.println(height);
        
        int width = (int) Math.round((trProjected.x() - blProjected.x())
                / (trProjected.y() - blProjected.y()) * height);
        
        System.out.println(width);
        
        Map map = osmFileToMap(osmFile);
        
        Java2DCanvas canvas = new Java2DCanvas(blProjected, trProjected, width,
                height, res, Color.WHITE);
        
        SwissPainter.painter().drawMap(map, canvas);
        
        ReliefShader shader = new ReliefShader(projection,
                new HGTDigitalElevationModel(new File(hgtFile)), new Vector3d(
                        -1.0, 1.0, 1.0));
        
        BufferedImage shade = shader.shadedRelief(trProjected, blProjected,
            width, height, 1.7);
        
        BufferedImage finalImage = merge(canvas, shade, width, height);
        
        ImageIO.write(finalImage, "png", new File(outputFile));
    }
    
    private static Map osmFileToMap(String osmFile) throws IOException,
            SAXException {
        CH1903Projection projection = new CH1903Projection();
        
        final boolean unGZip = osmFile.substring(osmFile.lastIndexOf('.') + 1)
                .equals("gz");
        OSMMap osmMap = OSMMapReader.readOSMFile(osmFile, unGZip);
        
        return new OSMToGeoTransformer(projection).transform(osmMap);
    }
    
    private static BufferedImage merge(Java2DCanvas canvas,
            BufferedImage shade, int width, int height) {
        BufferedImage finalImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        
        for (int x = 0; x != width; ++x) {
            for (int y = 0; y != height; ++y) {
                Color mapColor = Color.rgb(canvas.image().getRGB(x, y));
                Color shadeColor = Color.rgb(shade.getRGB(x, y));
                
                finalImage.setRGB(x, y, mapColor.multiply(shadeColor)
                        .toJavaColor()
                        .getRGB());
            }
        }
        return finalImage;
    }
    
}