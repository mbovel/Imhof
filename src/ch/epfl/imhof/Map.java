/**
 * 
 */
package ch.epfl.imhof;

import java.util.List;

import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * @author Matthieu Bovel (250300)
 */
public final class Map {
    public Map(List<Attributed<PolyLine>> ls, List<Attributed<Polygon>> ps) {
        // TODO Auto-generated constructor stub
    }

    public List<Attributed<Polygon>> polygons() {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Attributed<PolyLine>> polyLines() {
        // TODO Auto-generated method stub
        return null;
    }

    static public class Builder {
        public void addPolygon(Attributed<Polygon> ap) {
            // TODO Auto-generated method stub

        }

        public void addPolyLine(Attributed<PolyLine> al) {
            // TODO Auto-generated method stub

        }

        public Map build() {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
