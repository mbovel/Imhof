package ch.epfl.imhof.painting;

import java.util.function.Predicate;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.painting.LineStyle.LineCap;
import ch.epfl.imhof.painting.LineStyle.LineJoin;

public class RoadPainterGenerator {
    
    private RoadPainterGenerator() {
        
    }
    
    public static Painter painterForRoads(RoadSpec... specs) {
        
        return bridgesIn(specs).above(
            bridgesBorder(specs).above(
                roadsIn(specs).above(roadsBorder(specs).above(tunnels(specs)))));
    }
    
    private static Painter tunnels(RoadSpec... specs) {
        boolean firstSpec = true;
        Painter tunnelsPainter = specs[0].tunnels();
        for (RoadSpec roadSpec : specs) {
            if (firstSpec) {
                firstSpec = false;
            }
            
            else {
                tunnelsPainter = tunnelsPainter.above(roadSpec.tunnels());
            }
        }
        return tunnelsPainter;
    }
    
    private static Painter roadsBorder(RoadSpec... specs) {
        boolean firstSpec = true;
        Painter roadsBorderPainter = specs[0].roadsBorder();
        for (RoadSpec roadSpec : specs) {
            if (firstSpec) {
                firstSpec = false;
            }
            
            else {
                roadsBorderPainter = roadsBorderPainter.above(roadSpec.roadsBorder());
            }
        }
        
        return roadsBorderPainter;
    }
    
    private static Painter roadsIn(RoadSpec... specs) {
        boolean firstSpec = true;
        Painter roadsInPainter = specs[0].roadsIn();
        for (RoadSpec roadSpec : specs) {
            if (firstSpec) {
                firstSpec = false;
            }
            
            else {
                roadsInPainter = roadsInPainter.above(roadSpec.roadsIn());
            }
        }
        
        return roadsInPainter;
    }
    
    private static Painter bridgesBorder(RoadSpec... specs) {
        boolean firstSpec = true;
        Painter bridgesBorderPainter = specs[0].BridgesBorder();
        for (RoadSpec roadSpec : specs) {
            if (firstSpec) {
                firstSpec = false;
            }
            
            else {
                bridgesBorderPainter = bridgesBorderPainter.above(roadSpec.BridgesBorder());
            }
        }
        return bridgesBorderPainter;
    }
    
    private static Painter bridgesIn(RoadSpec... specs) {
        boolean firstSpec = true;
        Painter bridgesInPainter = specs[0].bridgesIn();
        for (RoadSpec roadSpec : specs) {
            if (firstSpec) {
                firstSpec = false;
            }
            
            else {
                bridgesInPainter = bridgesInPainter.above(roadSpec.bridgesIn());
            }
        }
        return bridgesInPainter;
    }
    
    public static class RoadSpec {
        private Predicate<Attributed<?>> filter;
        private float                    wayI, wayC;
        private Color                    colorI, colorC;
        
        public RoadSpec(Predicate<Attributed<?>> filter, float wayI,
                Color colorI, float wayC, Color colorC) {
            this.filter = filter;
            this.wayI = wayI;
            this.colorI = colorI;
            this.wayC = wayC;
            this.colorC = colorC;
        }
        
        public Painter tunnels() {
            float[] dashingPattern = { 2f * wayI, 2f * wayI };
            return Painter.line(wayI / 2f, colorC, LineCap.BUTT,
                LineJoin.ROUND, dashingPattern).when(
                filter.and(Filters.tagged("tunnel")));
        }
        
        public Painter roadsBorder() {
            return Painter.line(wayI + 2f * wayC, colorC, LineCap.ROUND,
                LineJoin.ROUND, new float[0])
                    .when(
                        (filter.and(Filters.tagged("bridge").negate())).and(Filters.tagged(
                            "tunnel")
                                .negate()));
        }
        
        public Painter BridgesBorder() {
            return Painter.line(wayI + 2f * wayC, colorC, LineCap.BUTT,
                LineJoin.ROUND, new float[0]).when(
                filter.and(Filters.tagged("bridge")));
        }
        
        public Painter roadsIn() {
            return Painter.line(wayI, colorI, LineCap.ROUND, LineJoin.ROUND,
                new float[0])
                    .when(
                        (filter.and(Filters.tagged("bridge").negate())).and(Filters.tagged(
                            "tunnel")
                                .negate()));
        }
        
        public Painter bridgesIn() {
            return Painter.line(wayI, colorI, LineCap.ROUND, LineJoin.ROUND,
                new float[0]).when(filter.and(Filters.tagged("bridge")));
        }
        
    }
}
