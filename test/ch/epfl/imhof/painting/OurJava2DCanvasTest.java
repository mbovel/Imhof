package ch.epfl.imhof.painting;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.Test;

import ch.epfl.imhof.geometry.OpenPolyLine;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;
import ch.epfl.imhof.testUtils.OurTestsUtils;

public class OurJava2DCanvasTest {
    @Test
    public void drawSimplePolyLine72dpiWorks() throws IOException {
        OurTestsUtils.assertImagesSame(
            "test/data/simplePolyLine72dpi.png",
            drawAmazingRedLine(72));
    }
    
    @Test
    public void drawSimplePolyLine300dpiWorks() throws IOException {
        OurTestsUtils.assertImagesSame(
            "test/data/simplePolyLine300dpi.png",
            drawAmazingRedLine(300));
    }
    
    @Test
    public void drawSimplePolygon72dpiWorks() throws IOException {
        OurTestsUtils.assertImagesSame(
            "test/data/simplePolygon72dpi.png",
            drawCrazyGreenPolygon(72));
    }
    
    @Test
    public void drawSimplePolygon300dpiWorks() throws IOException {
        OurTestsUtils.assertImagesSame(
            "test/data/simplePolygon300dpi.png",
            drawCrazyGreenPolygon(300));
    }
    
    static private BufferedImage drawAmazingRedLine(int res) {
        Java2DCanvas canvas = new Java2DCanvas(new Point(0, 0), new Point(
            200,
            200), 1500, 1500, res, Color.BLUE);
        
        PolyLine.Builder polyLineBuilder = new PolyLine.Builder();
        
        polyLineBuilder.addPoint(new Point(15, 15));
        polyLineBuilder.addPoint(new Point(33, 6));
        polyLineBuilder.addPoint(new Point(34, 60));
        polyLineBuilder.addPoint(new Point(60, 26));
        polyLineBuilder.addPoint(new Point(200, 200));
        
        OpenPolyLine polyLine = polyLineBuilder.buildOpen();
        
        LineStyle lineStyle = new LineStyle(1, Color.RED);
        
        canvas.drawPolyLine(polyLine, lineStyle);
        
        return canvas.image();
    }
    
    static private BufferedImage drawCrazyGreenPolygon(int res) {
        Java2DCanvas canvas = new Java2DCanvas(new Point(0, 0), new Point(
            200,
            200), 800, 800, 72, Color.RED);
        
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
        
        canvas.drawPolygon(polygon.build(), Color.GREEN);
        
        return canvas.image();
    }
}
