package ch.epfl.imhof.painting;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.epfl.imhof.painting.LineStyle.*;

public class OurLineStyleTest {
    private static final double    DELTA = 0.000001;
    private static final float    FDELTA = 0.000001f;
    private static final LineStyle STYLE = new LineStyle(4f, Color.GREEN,
                                                 LineCap.BUTT, LineJoin.BEVEL,
                                                 new float[0]);
    
    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionWithNegativeWidth() {
        new LineStyle(-3.2f, Color.RED, LineCap.BUTT, LineJoin.MITER,
                new float[0]);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionWithNegativeDashinPattern() {
        float[] negValue = { 2.3f, 3.4f, -2.3f };
        new LineStyle(3.2f, Color.RED, LineCap.BUTT, LineJoin.MITER, negValue);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionWithZeroDashinPattern() {
        float[] negValue = { 2.3f, 3.4f, 0f };
        new LineStyle(3.2f, Color.RED, LineCap.BUTT, LineJoin.MITER, negValue);
    }
    
    @Test
    public void shortConstructorReturnsRightValues() {
        LineStyle a = new LineStyle(2f, Color.rgb(0.4f, 0.6f, 0.8f));
        
        assertEquals(2f, a.width(), DELTA);
        assertEquals(Color.rgb(0x6699CC).r(), a.color().r(), DELTA);
        assertEquals(Color.rgb(0x6699CC).g(), a.color().g(), DELTA);
        assertEquals(Color.rgb(0x6699CC).b(), a.color().b(), DELTA);
        assertEquals(LineCap.BUTT, a.cap());
        assertEquals(LineJoin.MITER, a.join());
        assertArrayEquals(new float[0], a.dashingPattern(), FDELTA);
    }
    
    @Test
    public void withWidthReturnsRightWidth() {
       LineStyle a = STYLE.withWidth(2f);
       
       assertEquals(2f,a.width(), FDELTA);
    }
    
    @Test
    public void withColorReturnsRighColor() {
       LineStyle a = STYLE.withColor(Color.RED);
       
       assertEquals(Color.RED.r(), a.color().r(), FDELTA);
       assertEquals(Color.RED.g(), a.color().g(), FDELTA);
       assertEquals(Color.RED.b(), a.color().b(), FDELTA);
    }
    
    @Test
    public void withCapReturnsRightCap(){
        LineStyle a = STYLE.withCap(LineCap.ROUND);
        
        assertEquals(LineCap.ROUND, a.cap());
    }
    
    @Test
    public void withJoinReturnsRightJoin(){
        LineStyle a = STYLE.withJoin(LineJoin.BEVEL);
        
        assertEquals(LineJoin.BEVEL, a.join());
    }
    
    @Test
    public void withDashingPatternReturnsRightDashingPattern(){
        float[] dash = {2f,3f};
        LineStyle a = STYLE.withDashingPattern(dash);
        
        assertArrayEquals(dash, a.dashingPattern(), FDELTA);
    }
    
    
}
