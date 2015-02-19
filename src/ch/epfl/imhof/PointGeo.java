package ch.epfl.imhof;

public final class PointGeo {

	private final double longitude;
	private final double latitude;

	public PointGeo(double longitude, double latitude) {

		if (latitude < -(java.lang.Math.PI) / 2
				|| latitude > (java.lang.Math.PI) / 2) {
			throw new IllegalArgumentException();
		}

		if (longitude < -(java.lang.Math.PI) || longitude > java.lang.Math.PI) {
			throw new IllegalArgumentException();
		}

		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double longitude() {
		return longitude;
	}

	public double latitude() {
		return latitude;
	}

}
