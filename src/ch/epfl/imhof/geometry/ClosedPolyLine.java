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
		double area = 0;

		for (int i = 0; i != points.size(); ++i) {
			Point p1 = points.get(i);
			Point p2 = points.get(indiceGen(i + 1));

			area += (p1.x() * p2.y() - p2.x() * p1.y());
		}

		return area;
	}

	public boolean containsPoint(Point p) {
		int indice = 0;
		for (int i = 0; i != points.size(); ++i) {
			Point p1 = points.get(i);
			Point p2 = points.get(indiceGen(i + 1));

			if (p1.y() <= p.y()) {
				if (p2.y() > p.y() && isLeft(p, p1, p2)) {
					++indice;
				}
			}

			else if (p2.y() <= p.y() && isLeft(p, p2, p1)) {
				--indice;
			}
		}
		return (indice != 0);
	}

	private boolean isLeft(Point p, Point p1, Point p2) {

		return ((p1.x() - p.x()) * (p2.y() - p.y()) > (p2.x() - p.x())
				* (p1.y() - p.y()));
	}

	private int indiceGen(int indice) {

		return (java.lang.Math.floorMod(indice, points.size()));
	}
}
