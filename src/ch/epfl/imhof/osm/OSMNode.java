package ch.epfl.imhof.osm;

import ch.epfl.imhof.*;

/**
 * @author Matteo Besançon (245826)
 *
 *         Represents an <code>OSMNode</code> with an identity (<code>id</code>)
 *         and an {@link Attributes}.
 */
public final class OSMNode extends OSMEntity {

	private final PointGeo position;

	/**
	 * Construct a new <code>OSMNode</code> given its identification,
	 * <code>atributes</code> and <code>position</code>.
	 * 
	 * @param id
	 *            <code>OSMNode</code>'s identification.
	 * @param position
	 *            <code>OSMNode</code>'s position ({@link PointGeo}).
	 * @param attributes
	 *            <code>OSMNode</code>'s attributes ({@link Attributes}).
	 */
	public OSMNode(long id, PointGeo position, Attributes attributes) {
		super(id, attributes);
		this.position = position;

	}

	/**
	 * Returns the <code>position</code> of the <code>OSMNode</code>.
	 * 
	 * @return the <code>position</code> of the <code>OSMNode</code>.
	 */
	public PointGeo position() {
		return position;
	}

	/**
	 * Classe that helps in the construction of {@link OSMNode}.
	 * 
	 * @author Matteo Besançon (245826)
	 *
	 */
	public final static class Builder extends OSMEntity.Builder {
		private final PointGeo position;

		/**
		 * Constructs an <code>OSMNode.Builder</code> with the <code>id</code>
		 * and the <code>position</code> of the futur <code>OSMNode</code>.
		 * 
		 * @param id
		 *            the futur <code>OSMNode</code>'s identification.
		 * @param position
		 *            the futur <code>OSMNode</code>'s <code>position</code>.
		 */
		public Builder(long id, PointGeo position) {
			super(id);
			this.position = position;
		}

		/**
		 * Constructs a new <code>OSMNode</code> instance using the data
		 * provided by the <code>OSMNode.Builder</code>.
		 * 
		 * @return the new <code>OSMNode</code>.
		 * 
		 * @throws IllegalStateException
		 *             when the <code>OSMNode</code> is not complete (
		 *             <code>incomplete = true</code>).
		 */
		public OSMNode build() throws IllegalStateException {
			if (incomplete) {
				throw new IllegalStateException(
						"the OSMNode is not complete yet.");
			}
			return new OSMNode(this.id, this.position, this.attributes.build());
		}
	}
}
