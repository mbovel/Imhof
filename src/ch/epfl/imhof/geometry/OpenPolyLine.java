package ch.epfl.imhof.geometry;

import java.util.List;

/**
 * 
 * @author matteo113
 *
 */
public final class OpenPolyLine extends PolyLine {

	/**
	 * construct an open polyline by calling the constructor from the super class PolyLine
	 * 
	 *@param list of point of the polyline
	 */
	public OpenPolyLine(List<Point> points) {
		super(points);
	}
	/**
	 *@return return false because the Polyline isn't closed
	 */
	public boolean isClosed() {
		return (false);
	}
}
