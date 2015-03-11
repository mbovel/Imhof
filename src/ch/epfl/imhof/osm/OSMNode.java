package ch.epfl.imhof.osm;

import ch.epfl.imhof.*;

/**
 * @author Matteo Besançon (245826)
 *
 *         Represents an OSM node with an identity (long), an object Attributes
 *         which contains a map of attribute and a position (PointGeo).
 */
public final class OSMNode extends OSMEntity {

	private final PointGeo position;

	/**
	 * Create a new osm node given its identification, attributes and position.
	 * 
	 * @param id
	 *            identification of the osm node
	 * @param position
	 *            position of the osm node
	 * @param attributes
	 *            all the attributes of the osm node
	 */
	public OSMNode(long id, PointGeo position, Attributes attributes) {
		super(id, attributes);
		this.position = position;

	}

	/**
	 * return the position of the node
	 * 
	 * @return the position of the node
	 */
	public PointGeo position() {
		return position;
	}

	public final static class Builder extends OSMEntity.Builder {
		private final PointGeo position;

		public Builder(long id, PointGeo position) {
			super(id);
			this.position = position;
		}

		public OSMNode build() throws IllegalStateException {
			if (incomplete) {
				throw new IllegalStateException(
						"the OSMNode is not complete yet.");
			}
			return new OSMNode(this.id, this.position, this.attributes.build());
		}
	}
}
