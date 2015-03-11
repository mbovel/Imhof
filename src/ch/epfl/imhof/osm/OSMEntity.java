package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;

/**
 * @author Matteo Besançon (245826)
 * 
 *         Represents an OSM entity with an identity (<code>long</code>) and an
 *         object {@link Attributes} which contains a map of attribute.
 *
 */
public abstract class OSMEntity {
	private final long id;
	private final Attributes attributes;

	/**
	 * Construct a new <code>OSMEntity</code> from an identification and an
	 * <code>attributes</code>.
	 * 
	 * @param id
	 *            identification of the <code>OSMEntity</code>.
	 * @param attributes
	 *            all the attributes of the <code>OSMEntity</code>.
	 */
	public OSMEntity(long id, Attributes attributes) {
		this.attributes = attributes;
		this.id = id;
	}

	/**
	 * Returns the identification of the <code>OSMEntity</code>.
	 * 
	 * @return the id of the <code>OSMEntity</code>.
	 */
	public long id() {
		return id;
	}

	/**
	 * Returns the <code>attributes</code> of the <code>OSMEntity</code>.
	 * 
	 * @return the <code>attributes</code> of the <code>OSMEntity</code>.
	 */
	public Attributes attributes() {
		return attributes;
	}

	/**
	 * Returns <code>true</code> if the <code>attributes</code> field of the
	 * entity contains the attribute whith the same key.
	 * 
	 * @param key
	 *            the key of the attribute searched in <code>attributes</code>.
	 * @return <code>true</code> if the <code>attributes</code> field of the
	 *         entity contains the attribute whith the same key.
	 */
	public boolean hasAttribute(String key) {
		return attributes.contains(key);
	}

	/**
	 * Returns the value which correspond to a specified key or
	 * <code>null</code> if <code>attributes</code> doesn't contain this key
	 * 
	 * @param key
	 *            the <code>key</code> of the value to be returned.
	 * @return the value which correspond to a specified <code>key</code> or
	 *         <code>null</code> if <code>attributes</code> doesn't contain this
	 *         key.
	 */
	public String attributeValue(String key) {
		return attributes.get(key);
	}

	/**
	 * A class that helps in the construction of {@link OSMEntity}. It uses
	 * {@link Attributes.Builder} to create a new {@link Attributes}.
	 * 
	 * @author Matteo Besançon (245826)
	 *
	 */
	public abstract static class Builder {
		protected final long id;
		protected Attributes.Builder attributes = new Attributes.Builder();
		protected boolean incomplete;

		/**
		 * Had to put those in protected otherways it won't work but don't know
		 * why ....
		 */

		/**
		 * Construct an OSMEntity.Builder with an <code>id</code>.
		 * 
		 * @param id
		 *            the future OSMEntity's identification.
		 */
		public Builder(long id) {
			this.id = id;
		}

		/**
		 * Sets an attribute (<code>key</code>, <code>value</code>) using the
		 * method {@link Attributes.Builder.put}.
		 * 
		 * @param key
		 *            attribute's key
		 * @param value
		 *            attribute's value
		 */
		public void setAttribute(String key, String value) {
			attributes.put(key, value);
		}

		/**
		 * Sets the value of <code>incomplete</code> to <code>true</code>.
		 */
		public void setIncomplete() {
			incomplete = true;
		}

		/**
		 * Returns the value of <code>incomplete</code>.
		 * 
		 * @return <code>true</code> if the futur <code>OSMEntity</code> is not
		 *         complete.
		 */
		public boolean isIncomplete() {
			return incomplete;
		}

	}

}
