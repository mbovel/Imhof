package ch.epfl.imhof.geometry;

import java.util.List;

/**
 * Polyline are represented by a list of points
 * 
 * @author matteo113
 *
 */
public abstract class PolyLine {
	private final List<Point> points;

	/**
	 * Constructs a Polyline given a list of point
	 * 
	 * @param points
	 *            list of all points in the polyLine
	 * 
	 * @throws IllegalArgumentException
	 *             if points is empty
	 */

	public PolyLine(List<Point> points) throws IllegalArgumentException {
		if (points.isEmpty()) {
			throw new IllegalArgumentException("Polyline size must not be null");
		}
		this.points = points;
	}

	/**
	 *@return true if the polyline is closed
	 */
	public abstract boolean isClosed();
	
	/**
	 *@return the List of point
	 */
	public List<Point> points() {
		return (points);
	}

	/**
	 *@return the first point of the list
	 */
	public Point firstPoint() {
		return (points.get(0));
	}

}
