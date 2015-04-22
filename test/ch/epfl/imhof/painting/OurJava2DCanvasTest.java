package ch.epfl.imhof.painting;

import static org.junit.Assert.*;

import java.awt.BasicStroke;
import java.io.IOException;

import org.junit.Test;

import ch.epfl.imhof.geometry.OpenPolyLine;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;
import ch.epfl.imhof.painting.LineStyle.LineCap;
import ch.epfl.imhof.painting.LineStyle.LineJoin;

public class OurJava2DCanvasTest {
    
    @Test
    public void imageTest() throws IOException {
        Java2DCanvas a = new Java2DCanvas(new Point(1, -1), new Point(8, 4),
                134, 500, 500, Color.GREEN);
        a.paint("test2");
    }
    
    @Test
    public void drawPolyLineTest() throws IOException {
        Java2DCanvas a = new Java2DCanvas(new Point(0, 0), new Point(200, 200),
                1500, 1500, 72, Color.BLUE);
        
        PolyLine.Builder b = new PolyLine.Builder();
        
        b.addPoint(new Point(15, 15));
        b.addPoint(new Point(33, 6));
        b.addPoint(new Point(34, 60));
        b.addPoint(new Point(60, 26));
        b.addPoint(new Point(200, 200));
        
        OpenPolyLine c = b.buildOpen();
        
        LineStyle d = new LineStyle(10, Color.RED, LineCap.ROUND,
                LineJoin.ROUND, null);
        
        a.drawPolyLine(c, d);
        
        a.paint("test2");
        
    }
    
    @Test
    public void drawPolygonTest() throws IOException {
        Java2DCanvas a = new Java2DCanvas(new Point(0, 0), new Point(200, 200),
                800, 800, 72, Color.RED);
        
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
        
        a.paint("test3");
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
