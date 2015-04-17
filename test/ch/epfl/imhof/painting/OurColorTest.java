package ch.epfl.imhof.painting;

import static org.junit.Assert.*;

import org.junit.Test;

public class OurColorTest {
    private static final double DELTA = 0.000001;
    
    @Test
    public void rgbIntReturnsWorksWithSimpleColor() {
        Color color = Color.rgb(0x6699CC); 
        
        assertEquals(0.4, color.r(), DELTA);
        assertEquals(0.6, color.g(), DELTA);
        assertEquals(0.8, color.b(), DELTA);
    }
    
    @Test
    public void rgbIntReturnsWorksWithMinValue() {
        Color color = Color.rgb(0x000000); 
        
        assertEquals(0.0, color.r(), DELTA);
        assertEquals(0.0, color.g(), DELTA);
        assertEquals(0.0, color.b(), DELTA);
    }
    
    @Test
    public void rgbIntReturnsWorksWithMaxValue() {
        Color color = Color.rgb(0xFFFFFF); 
        
        assertEquals(1.0, color.r(), DELTA);
        assertEquals(1.0, color.g(), DELTA);
        assertEquals(1.0, color.b(), DELTA);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void rgbIntThrowsExceptionWithTooSmallValue() {
        Color.rgb(-1); 
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void rgbIntThrowsExceptionWithTooBigValue() {
        Color.rgb(0x2FFFFFF); 
        
    }
}
