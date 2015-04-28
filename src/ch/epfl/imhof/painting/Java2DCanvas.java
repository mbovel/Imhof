package ch.epfl.imhof.painting;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.function.Function;

import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

public class Java2DCanvas implements Canvas {
    private final Function<Point, Point> change;
    private final BufferedImage          image;
    private final Graphics2D             context;
    
    public Java2DCanvas(Point bl, Point tr, int width, int height,
            int resolution, Color background) {
        double resolutionFactor = resolution / 72.0;
        double relWidth = width / resolutionFactor;
        double relHeight = height / resolutionFactor;
        
        change = Point.alignedCoordinateChange(
            bl,
            new Point(0, relHeight),
            tr,
            new Point(relWidth, 0));
        
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        // create the graphical context
        context = image.createGraphics();
        
        context.scale(resolutionFactor, resolutionFactor);
        
        // set on the antialiasing
        context.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        
        // fill the background
        context.setColor(background.toJavaColor());
        context.fillRect(0, 0, (int) relHeight + 1, (int) relWidth + 1);
        
    }
    
    public BufferedImage image() {
        return image;
    }
    
    @Override
    public void drawPolyLine(PolyLine toDraw, LineStyle style) {
        BasicStroke stroke = lineStyleToBasicStroke(style);
        
        context.setStroke(stroke);
        context.setColor(style.color().toJavaColor());
        
        context.draw(polyLineToPath2D(toDraw));
    }
    
    @Override
    public void drawPolygon(Polygon toDraw, Color color) {
        Path2D path = polyLineToPath2D(toDraw.shell());
        Area area = new Area(path);
        
        for (ClosedPolyLine hole : toDraw.holes()) {
            area.subtract(new Area(polyLineToPath2D(hole)));
        }
        
        context.setColor(color.toJavaColor());
        
        context.fill(area);
        context.draw(area);
    }
    
    private Path2D polyLineToPath2D(PolyLine toDraw) {
        Path2D path = new Path2D.Double();
        boolean first = true;
        
        for (Point point : toDraw.points()) {
            Point relPoint = change.apply(point);
            
            if (first) {
                path.moveTo(relPoint.x(), relPoint.y());
            }
            else {
                path.lineTo(relPoint.x(), relPoint.y());
            }
            
            first = false;
        }
        
        if (toDraw.isClosed()) {
            path.closePath();
        }
        
        return path;
    }
    
    static private BasicStroke lineStyleToBasicStroke(LineStyle style) {
        return new BasicStroke(
            style.width(),
            convertCap(style),
            convertJoin(style),
            10.0f,
            style.dashingPattern(),
            0.0f);
    }
    
    static private int convertCap(LineStyle style) {
        int value = 0;
        
        switch (style.cap()) {
            case ROUND:
                value = BasicStroke.CAP_ROUND;
                break;
            case BUTT:
                value = BasicStroke.CAP_BUTT;
                break;
            case SQUARE:
                value = BasicStroke.CAP_SQUARE;
                break;
        }
        
        return value;
    }
    
    static private int convertJoin(LineStyle style) {
        int value = 0;
        
        switch (style.join()) {
            case BEVEL:
                value = BasicStroke.JOIN_BEVEL;
                break;
            case ROUND:
                value = BasicStroke.JOIN_ROUND;
                break;
            case MITER:
                value = BasicStroke.JOIN_MITER;
                break;
        }
        
        return value;
    }
}
