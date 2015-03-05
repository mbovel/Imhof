package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;

/**
 * @author Matteo Besançon (245826)
 * 
 *         Represents an OSM entity with an identity (long) and an object
 *         Attributes which contains a map of attribute.
 *
 */
public class OSMEntity {
	private long id;
	private Attributes attributes;

	/**
	 * create a new osm object given its identification and attributes
	 * 
	 * @param id
	 *            identification of the osm object
	 * @param attributes
	 *            all the attributes of the osm object
	 */
	public OSMEntity(long id, Attributes attributes) {
		this.attributes = attributes;
		this.id = id;
	}

	/**
	 * return the identification of the osm entity
	 * 
	 * @return the id of the osm entity
	 */
	public long id() {
		return id;
	}

	/**
	 * return the attributes of the osm entity
	 * 
	 * @return the attributes of the osm entity
	 */
	public Attributes attributes() {
		return attributes;
	}

	/**
	 * return true if the Attributes field of the entity contains the attribute
	 * whith the same key
	 * 
	 * @param key
	 *            the key of the attribute searched in attributes
	 * @return true if the Attributes field of the entity contains the attribute
	 *         whith the same key
	 */
	public boolean hasAttribute(String key) {
		return attributes.contains(key);
	}

	/**
	 * return the value which correspond to a specified key or null if
	 * attributes doesn't contain this key
	 * 
	 * @param key
	 *            of the value to be returned
	 * @return the value which correspond to a specified key or null if
	 *         attributes doesn't contain this key
	 */
	public String attributeValue(String key) {
		return attributes.get(key);
	}

}