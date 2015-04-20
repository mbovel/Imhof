package ch.epfl.imhof.painting;

import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

public interface Canevas {
    
    public PolyLine drawPolyLine(LineStyle style);
    
    public void drawPolygon(Polygon toDraw);
    
}
