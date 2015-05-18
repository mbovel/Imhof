package ch.epfl.imhof.dem;

import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.Vector3d;
import ch.epfl.imhof.projection.Projection;

public class ReliefShader {
    private Vector3d              sun;
    private DigitalElevationModel dem;
    private Projection            projection;
    
    public ReliefShader(Projection projection, DigitalElevationModel dem,
            Vector3d sun) {
        this.projection = projection;
        this.dem = dem;
        this.sun = sun;
    }
    
    public void shadedrelief(Point topRight, Point bottomleft, int width,
            int height, int blurRaduius) {
           
    }
    
    private double[][] kernel(){
         
    }
    
}
