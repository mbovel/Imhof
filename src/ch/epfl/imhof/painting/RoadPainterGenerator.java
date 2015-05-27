package ch.epfl.imhof.painting;

import java.util.function.Function;
import java.util.function.Predicate;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.painting.LineStyle.LineCap;
import ch.epfl.imhof.painting.LineStyle.LineJoin;

/**
 * A class that generate a painter which is able to paint the whole road network
 * of a {@link Map}.
 * 
 * @author Matthieu Bovel (250300)
 * @author Matteo Besançon (245826)
 */
public final class RoadPainterGenerator {
    private RoadPainterGenerator() {
        
    }
    
    /**
     * A method that returns a painter which is able to paint the whole road
     * network of a {@link Map}.
     * 
     * @param specs
     *            a list of {@link RoadSpec RoadSpecs}
     * 
     * @return a painter which is able to paint the whole road network of a
     *         {@link Map}
     */
    public static Painter painterForRoads(RoadSpec... specs) {
        if (specs.length == 0) {
            return Painter.empty();
        }
        
        return stack(specs, RoadSpec::bridgesIn).above(
            stack(specs, RoadSpec::bridgesBorder))
                .above(stack(specs, RoadSpec::roadsIn))
                .above(stack(specs, RoadSpec::roadsBorder))
                .above(stack(specs, RoadSpec::tunnels));
    }
    
    private static Painter stack(RoadSpec[] specs,
            Function<RoadSpec, Painter> getter) {
        Painter tunnelsPainter = getter.apply(specs[0]);
        
        for (int i = 1; i != specs.length; ++i) {
            tunnelsPainter = tunnelsPainter.above(getter.apply(specs[i]));
        }
        
        return tunnelsPainter;
    }
    
    /**
     * Holds informations about the way of drawing a road.
     * 
     * @author Matteo Besançon (245826)
     */
    public static class RoadSpec {
        private final Predicate<Attributed<?>> filter;
        private final float                    wayI, wayC;
        private final Color                    colorI, colorC;
        
        /**
         * Construct a RoadSpec instance
         * 
         * @param filter
         *            the filter used to choose the roads
         * @param wayI
         *            the thickness of inside of the road
         * @param colorI
         *            the color of the inside of the road
         * @param wayC
         *            the thickness of the border of the road
         * @param colorC
         *            the color of the border of the road
         */
        public RoadSpec(Predicate<Attributed<?>> filter, float wayI,
                Color colorI, float wayC, Color colorC) {
            this.filter = filter;
            this.wayI = wayI;
            this.colorI = colorI;
            this.wayC = wayC;
            this.colorC = colorC;
        }
        
        private Painter tunnels() {
            float[] dashingPattern = { 2f * wayI, 2f * wayI };
            
            return Painter.line(wayI / 2f, colorC, LineCap.BUTT,
                LineJoin.ROUND, dashingPattern).when(
                filter.and(Filters.tagged("tunnel")));
        }
        
        private Painter roadsBorder() {
            return Painter.line(wayI + 2f * wayC, colorC, LineCap.ROUND,
                LineJoin.ROUND, new float[0]).when(
                filter.and(Filters.tagged("bridge").negate()).and(
                    Filters.tagged("tunnel").negate()));
        }
        
        private Painter bridgesBorder() {
            return Painter.line(wayI + 2f * wayC, colorC, LineCap.BUTT,
                LineJoin.ROUND, new float[0]).when(
                filter.and(Filters.tagged("bridge")));
        }
        
        private Painter roadsIn() {
            return Painter.line(wayI, colorI, LineCap.ROUND, LineJoin.ROUND,
                new float[0]).when(
                filter.and(Filters.tagged("bridge").negate()).and(
                    Filters.tagged("tunnel").negate()));
        }
        
        private Painter bridgesIn() {
            return Painter.line(wayI, colorI, LineCap.ROUND, LineJoin.ROUND,
                new float[0]).when(filter.and(Filters.tagged("bridge")));
        }
    }
}
