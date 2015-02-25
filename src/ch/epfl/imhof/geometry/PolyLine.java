package ch.epfl.imhof.geometry;

import java.util.List;

public abstract class PolyLine {
	private List<Point> points;

	public PolyLine(List<Point> points) throws IllegalArgumentException {
		if (points.isEmpty()) {
			throw new IllegalArgumentException("Polyline size must not be null");
		}
		this.points = points;
	}

	public abstract boolean isClosed();

	public List<Point> points() {
		return (points);
	}

	public Point firstPoint() {
		return (points.get(0));
	}

}
