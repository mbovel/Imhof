package ch.epfl.imhof.dem;

import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.function.Function;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.Vector3d;
import ch.epfl.imhof.projection.Projection;

/**
 * Class used to graphically represent a {@link DigitalElevationModel}.
 * 
 * @author Matthieu Bovel (250300)
 */
public final class ReliefShader {
    private final DigitalElevationModel dem;
    private final Projection            projection;
    private final Vector3d              sun;
    
    /**
     * Constructs a new <code>ReliefShader</code> given a {@link Projection}, a
     * {@link DigitalElevationModel} and the direction of the sunlight (a
     * {@link Vector3d}).
     * 
     * @param projection
     *            a {@link Projection} to use to transform 3D points of the
     *            {@link DigitalElevationModel} to 2D points for drawing
     * @param dem
     *            the {@link DigitalElevationModel}
     * @param sun
     *            a {@link Vector3d} representing the direction of the sunlight
     */
    public ReliefShader(Projection projection, DigitalElevationModel dem,
            Vector3d sun) {
        this.projection = projection;
        this.dem = dem;
        this.sun = sun.normalized();
    }
    
    /**
     * Draw a subset of the {@link DigitalElevationModel} data on a
     * {@link BufferedImage}.
     * 
     * @param topRight
     *            the {@link Point} a the top right of the image
     * @param bottomLeft
     *            the {@link Point} a the bottom left of the image
     * @param imgWidth
     *            width of the output image (in pixels)
     * @param imgHeight
     *            height of the output image (in pixels)
     * @param blurRadius
     *            radius of the gausian blur to apply to the final image (in
     *            pixels)
     * @return the result as a {@link BufferedImage}
     */
    public BufferedImage shadedRelief(Point topRight, Point bottomLeft,
            int imgWidth, int imgHeight, double blurRadius) {
        float[] blurKernel = blurKernel(blurRadius);
        int offset = blurKernel.length / 2;
        int bufferWidth = imgWidth + 2 * offset;
        int bufferHeight = imgHeight + 2 * offset;
        
        BufferedImage raw = rawShadedRelief(
            bufferWidth,
            bufferHeight,
            Point.alignedCoordinateChange(
                new Point(imgWidth + offset, offset),
                topRight,
                new Point(offset, offset + imgHeight),
                bottomLeft));
        
        return offset == 0 ? raw : blurImage(raw, blurKernel).getSubimage(
            offset,
            offset,
            imgWidth,
            imgHeight);
    }
    
    private BufferedImage rawShadedRelief(int width, int height,
            Function<Point, Point> coorChange) {
        BufferedImage image = new BufferedImage(
            width,
            height,
            BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y != height; ++y) {
            for (int x = 0; x != width; ++x) {
                PointGeo pointGeo = projection.inverse(coorChange
                    .apply(new Point(x, y)));
                Vector3d normal = dem.normalAt(pointGeo).normalized();
                
                // As vectors are normalized, we do not need to divide the
                // scalar product to get the cos of the angle between them.
                float cos = (float) normal.dot(sun);
                
                float red = 0.5f * (cos + 1);
                float green = red;
                float blue = 0.5f * (0.7f * cos + 1);
                
                Color color = new Color(red, green, blue);
                image.setRGB(x, y, color.getRGB());
            }
        }
        
        return image;
        
    }
    
    static protected float[] blurKernel(double blurRadius) {
        double sigma = blurRadius / 3;
        int ceiledBlurRadius = (int) Math.ceil(blurRadius);
        int middle = ceiledBlurRadius;
        int n = 2 * ceiledBlurRadius + 1;
        int last = n - 1;
        double sum = 0;
        float[] result = new float[n];
        
        for (int i = 0; i != middle; ++i) {
            float x = i - middle;
            float weight = (float) Math.pow(Math.E, -x * x
                    / (2 * sigma * sigma));
            sum += result[i] = result[last - i] = weight;
        }
        
        sum *= 2;
        sum += result[middle] = 1.0f;
        
        for (int i = 0; i != middle; ++i) {
            result[i] /= sum;
            result[last - i] /= sum;
        }
        
        result[middle] /= sum;
        
        return result;
    }
    
    static private BufferedImage blurImage(BufferedImage image, float[] kernel) {
        RenderingHints hints = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        ConvolveOp xBlur = new ConvolveOp(
            new Kernel(kernel.length, 1, kernel),
            ConvolveOp.EDGE_NO_OP,
            hints);
        
        ConvolveOp yBlur = new ConvolveOp(
            new Kernel(1, kernel.length, kernel),
            ConvolveOp.EDGE_NO_OP,
            hints);
        
        return yBlur.filter(xBlur.filter(image, null), null);
    }
}
