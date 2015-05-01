package ch.epfl.imhof.painting;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import ch.epfl.imhof.geometry.OpenPolyLine;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

public class OurJava2DCanvasTest {
    static private void canvasToFile(Java2DCanvas canvas, String fileName)
            throws IOException {
        ImageIO.write(canvas.image(), "png", new File(fileName + ".png"));
    }
    
    static private Java2DCanvas polyLineOnCanvas(int res) {
        Java2DCanvas canvas = new Java2DCanvas(
            new Point(0, 0),
            new Point(200, 200),
            1500,
            1500,
            res,
            Color.BLUE);
        
        PolyLine.Builder polyLineBuilder = new PolyLine.Builder();
        
        polyLineBuilder.addPoint(new Point(15, 15));
        polyLineBuilder.addPoint(new Point(33, 6));
        polyLineBuilder.addPoint(new Point(34, 60));
        polyLineBuilder.addPoint(new Point(60, 26));
        polyLineBuilder.addPoint(new Point(200, 200));
        
        OpenPolyLine polyLine = polyLineBuilder.buildOpen();
        
        LineStyle lineStyle = new LineStyle(
            1,
            Color.RED);
        
        canvas.drawPolyLine(polyLine, lineStyle);
        
        return canvas;
    }
    
    @Test
    public void drawPolyLine72dpiWorks() throws IOException {
        canvasToFile(polyLineOnCanvas(72), "test2_72dpi");
    }
    
    @Test
    public void drawPolyLine300dpiWorks() throws IOException {
        canvasToFile(polyLineOnCanvas(300), "test2_300dpi");
    }
    
    @Test
    public void drawPolygonWorks() throws IOException {
        Java2DCanvas a = new Java2DCanvas(
            new Point(0, 0),
            new Point(200, 200),
            800,
            800,
            72,
            Color.RED);
        
        PolyLine.Builder shell = new PolyLine.Builder();
        
        shell.addPoint(new Point(20, 20));
        shell.addPoint(new Point(20, 120));
        shell.addPoint(new Point(120, 120));
        shell.addPoint(new Point(120, 20));
        
        PolyLine.Builder hole = new PolyLine.Builder();
        
        hole.addPoint(new Point(35, 35));
        hole.addPoint(new Point(35, 70));
        hole.addPoint(new Point(50, 70));
        hole.addPoint(new Point(60, 55));
        hole.addPoint(new Point(70, 70));
        hole.addPoint(new Point(85, 70));
        hole.addPoint(new Point(85, 35));
        
        Polygon.Builder polygon = new Polygon.Builder(shell.buildClosed());
        
        polygon.addHole(hole.buildClosed());
        
        a.drawPolygon(polygon.build(), Color.GREEN);
        
        canvasToFile(a, "test3");
    }
    
    /*
     * @Test public void convertCapTest(){ Java2DCanvas canvas = new
     * Java2DCanvas(new Point(0, 0), new Point(200, 200), 1500, 1500, 72,
     * Color.BLUE);
     * 
     * LineStyle a = new LineStyle(20, Color.RED, LineCap.ROUND, LineJoin.ROUND,
     * null); LineStyle b = new LineStyle(20, Color.RED, LineCap.BUTT,
     * LineJoin.ROUND, null); LineStyle c = new LineStyle(20, Color.RED,
     * LineCap.SQUARE, LineJoin.ROUND, null);
     * 
     * assertEquals(BasicStroke.CAP_ROUND, canvas.convertCap(a));
     * assertEquals(BasicStroke.CAP_BUTT, canvas.convertCap(b));
     * assertEquals(BasicStroke.CAP_SQUARE, canvas.convertCap(c)); }
     * 
     * 
     * @Test public void convertJoinTest(){ Java2DCanvas canvas = new
     * Java2DCanvas(new Point(0, 0), new Point(200, 200), 1500, 1500, 72,
     * Color.BLUE);
     * 
     * LineStyle a = new LineStyle(20, Color.RED, LineCap.ROUND, LineJoin.ROUND,
     * null); LineStyle b = new LineStyle(20, Color.RED, LineCap.BUTT,
     * LineJoin.BEVEL, null); LineStyle c = new LineStyle(20, Color.RED,
     * LineCap.SQUARE, LineJoin.MITER, null);
     * 
     * assertEquals(BasicStroke.JOIN_ROUND, canvas.convertJoin(a));
     * assertEquals(BasicStroke.JOIN_BEVEL, canvas.convertJoin(b));
     * assertEquals(BasicStroke.JOIN_MITER, canvas.convertJoin(c)); }
     */
}
