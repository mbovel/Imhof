package ch.epfl.imhof.dem;

import static org.junit.Assert.*;

import org.junit.Test;

public class OurReliefShaderTest {
    private static final float DELTA = 0.000001f;
    
    @Test
    public void blurKernelWorksWithExample() {
        float[] result = ReliefShader.blurKernel(0.9);
        
        assertEquals(3, result.length);
        assertEquals(0.003836, result[0], DELTA);
        assertEquals(0.992327, result[1], DELTA);
        assertEquals(0.003836, result[2], DELTA);
    }
}
