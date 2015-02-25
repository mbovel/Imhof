package ch.epfl.imhof.geometry;

import java.util.List;

public final class ClosedPolyLine extends PolyLine {

	public ClosedPolyLine(List<Point> points) {
		super(points);
	}

	public boolean isClosed() {
		return (true);
	}

	public double area() {

	}

	public boolean containsPoint(Point p) {

	}
}
