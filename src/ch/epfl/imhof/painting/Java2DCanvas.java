package ch.epfl.imhof.painting;

import java.awt.geom.Area;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.imageio.ImageIO;

import ch.epfl.imhof.geometry.ClosedPolyLine;
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
        
        this.width = width * resolutionFactor;
        this.height = height * resolutionFactor;
        this.background = background;
        
        this.change = Point.alignedCoordinateChange(bl, new Point(0,
                this.height), tr, new Point(this.width, 0));
        
        this.image = new BufferedImage((int) this.width, (int) this.height,
                BufferedImage.TYPE_INT_RGB);
        
        // creat the graphical context
        this.context = image.createGraphics();
        
        // set on the antialiasing
        context.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        
        // fill the background
        context.setColor(background.toJavaColor());
        context.fillRect(0, 0, width, height);
        
        // context.scale(width * resolutionFactor, height * resolutionFactor);
        
    }
    
    public BufferedImage image() {
        return image;
    }
    
    // NOT FORGET TO SET VISIBILITY TO PRIVATE WHEN DONE WITH TESTING !!!!
    public void paint(String name) throws IOException {
        ImageIO.write(image, "png", new File(name + ".png"));
    }
    
    @Override
    public void drawPolyLine(PolyLine toDraw, LineStyle style) {
        BasicStroke stroke = styleToStroke(style);
        
        context.setStroke(stroke);
        context.setColor(style.color().toJavaColor());
        
        context.draw(stroke.createStrokedShape(creatPath(toDraw)));
    }
    
    private Path2D creatPath(PolyLine toDraw) {
        List<Point> toDrawPoints = changePoints(toDraw.points());
        Path2D path = new Path2D.Double();
        
        path.moveTo(change.apply(toDraw.firstPoint()).x(),
            change.apply(toDraw.firstPoint()).y());
        
        for (int i = 1; i != toDrawPoints.size(); ++i) {
            path.lineTo(toDrawPoints.get(i).x(), toDrawPoints.get(i).y());
        }
        
        if (toDraw.isClosed()) {
            path.closePath();
        }
        return path;
    }
    
    private List<Point> changePoints(List<Point> points) {
        List<Point> toReturn = new ArrayList<Point>();
        
        for (Point point : points) {
            toReturn.add(change.apply(point));
        }
        return toReturn;
    }
    
    private BasicStroke styleToStroke(LineStyle style) {
        return new BasicStroke(style.width(), convertCap(style),
                convertJoin(style), 10.0f, style.dashingPattern(), 0.0f);
    }
    
    private int convertCap(LineStyle style) {
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
    
    private int convertJoin(LineStyle style) {
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
    
    @Override
    public void drawPolygon(Polygon toDraw, Color color) {
        context.setColor(color.toJavaColor());
        Path2D path = creatPath(toDraw.shell());
        
        Area area = new Area(path);
        
        for (ClosedPolyLine hole : toDraw.holes()) {
            area.subtract(new Area(creatPath(hole)));
        }
        
        context.fill(area);
        context.draw(area);
    }
    
}
