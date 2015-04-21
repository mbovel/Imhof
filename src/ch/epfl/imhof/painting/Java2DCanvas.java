package ch.epfl.imhof.painting;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import javax.imageio.ImageIO;

import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

public class Java2DCanvas implements Canvas {
    private double                 width, height;
    private Color                  background;
    private Function<Point, Point> change;
    private BufferedImage          image;
    private Graphics2D             context;
    
    public Java2DCanvas(Point bl, Point tr, int width, int height,
            int resolution, Color background) {
        double resolutionFactor = (resolution / 72);
        
        this.width = width;
        this.height = height;
        this.background = background;
        
        this.change = Point.alignedCoordinateChange(bl, new Point(0, height),
            tr, new Point(width, 0));
        
        this.image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        
        // creat the graphical context
        this.context = image.createGraphics();
        
        // set on the antialiasing
        context.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        
        // fill the background
        context.setColor(background.toJavaColor());
        context.fillRect(0, 0, width, height);
        
        context.scale(width * resolutionFactor, height * resolutionFactor);
        
    }
    
    public BufferedImage image() {
        return image;
    }
    
    public void paint(String name) throws IOException {
        ImageIO.write(image, "png", new File(name + ".png"));
    }
    
    @Override
    public void drawPolyLine(PolyLine toDraw, LineStyle style) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void drawPolygon(Polygon toDraw, Color color) {
        // TODO Auto-generated method stub
        
    }
    
}
