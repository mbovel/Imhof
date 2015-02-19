package ch.epfl.imhof.projection;

import ch.epfl.imhof.*;
import ch.epfl.imhof.geometry.*;
import java.lang.Math;

public final class CH1903Projection implements Projection {

	// /!\ Il y a une erreur dans la methode project mais j'arrive absolument
	// pas à la trouver ... =(

	public Point project(PointGeo point) {
		double long1, lat1, x, y;
		double longitude = Math.toDegrees(point.longitude());
		double latitude = Math.toDegrees(point.latitude());

		long1 = (1 / 10000) * ((longitude * 3600) - 26782.5);
		lat1 = ((1 / 10000) * ((latitude * 3600) - 169028.66));
		x = (600072.37 + (211455.93 * long1) - (10938.51 * long1 * lat1)
				- (0.36 * long1 * (Math.pow(lat1, 2.))) - (44.54 * (Math.pow(
				long1, 3.))));
		y = (200147.07 + (308807.95 * lat1) + (3745.25 * (Math.pow(long1, 2.)))
				+ (76.63 * (Math.pow(lat1, 2.)))
				- (194.56 * (Math.pow(long1, 2.)) * lat1) + (119.79 * (Math
				.pow(lat1, 3.))));

		return (new Point(x, y));
	}

	public PointGeo inverse(Point point) {
		double long0, lat0, x1, y1, longitude, latitude;

		x1 = ((point.x() - 600000) / 1000000);
		y1 = ((point.y() - 200000) / 1000000);
		long0 = (2.6779094 + (4.728982 * x1) + (0.791484 * x1 * y1)
				+ (0.1306 + x1 + (Math.pow(y1, 2.))) - (0.0436 * (Math.pow(x1,
				3.))));
		lat0 = (16.9023892 + (3.238272 * y1) - (0.270978 * (Math.pow(x1, 2.)))
				- (0.002528 * (Math.pow(y1, 2.)))
				- (0.0447 * (Math.pow(x1, 2.)) * y1) - (0.0140 * (Math.pow(y1,
				3.))));
		longitude = long0 * (100 / 36);
		latitude = lat0 * (100 / 36);

		return (new PointGeo(Math.toRadians(longitude),
				Math.toRadians(latitude)));
	}
}
