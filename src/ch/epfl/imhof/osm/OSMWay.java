package ch.epfl.imhof.osm;

import java.util.Collections;
import java.util.List;

import ch.epfl.imhof.Attributes;

/**
 * An OSM way is a list of nodes represented by its identification, attributes
 * and a List<Node>.
 * 
 * @author Matteo Besançon (245826)
 *
 */
public final class OSMWay extends OSMEntity {

	private final List<OSMNode> nodes;

	/**
	 * Create a new osm way given its identification, attributes and a list of
	 * nodes.
	 * 
	 * @param id
	 *            identification of the osm way.
	 * @param nodes
	 *            list of the nodes making the way.
	 * @param attributes
	 *            attributes of the osm way.
	 * @throws IllegalArgumentException
	 *             if the list of nodes contains strictly less than two nodes.
	 */
	public OSMWay(long id, List<OSMNode> nodes, Attributes attributes)
			throws IllegalArgumentException {

		
		super(id, attributes);
		if (nodes.size() < 2) {
			throw new IllegalArgumentException(
					"nodes must contain at least two ellements");
		}
		this.nodes = Collections.unmodifiableList(nodes);

	}

	/**
	 * returns the number of nodes in the osm way /!\ it will count two times
	 * the first node if the way is closed.
	 * 
	 * @return the number of nodes in the osm way.
	 */
	public int nodesCount() {
		return nodes.size();
	}

	/**
	 * returns the list of all the nodes that made the osm way.
	 * 
	 * @return the list of all the nodes that made the osm way.
	 */
	public List<OSMNode> nodes() {
		return nodes;
	}

	/**
	 * return true if the osm way is closed. Formally it tests if the first
	 * nodes of the list is the same than the last with the method equals.
	 * 
	 * @return true if the osm way is closed.
	 */
	public boolean isClosed() {
		return nodes.get(0).equals(nodes.get(nodes.size() - 1));
	}

	/**
	 * return the list of all the nodes that made the osm way without repeating
	 * two times the same node if the first one is the same than the last one.
	 * 
	 * @return the list of all the nodes that made the osm way without repeating
	 *         two times the same node if the first one is the same than the
	 *         last one.
	 */
	public List<OSMNode> nonRepeatingNodes() {
		if (isClosed()) {
			return nodes.subList(0, nodes.size() - 1);
		}

		else
			return nodes;
	}

	/**
	 * return the first node of the list of nodes of the way.
	 * 
	 * @return the first node of the list of nodes of the way.
	 */
	public OSMNode firstNode() {
		return nodes.get(0);
	}

	/**
	 * return the last node of the list of nodes of the way.
	 * 
	 * @return the last node of the list of nodes of the way.
	 */
	public OSMNode lastNode() {
		return nodes.get(nodes.size() - 1);
	}

	public final static class Builder extends OSMEntity.Builder {
		private List<OSMNode> nodes;

		public Builder(long id) {
			super(id);
		}

		public void addNode(OSMNode newNode) {
			nodes.add(newNode);
		}

		public OSMWay build() throws IllegalStateException {
			if (incomplete) {
				throw new IllegalStateException(
						"The OSMWay is not complete yet.");
			}
			return new OSMWay(this.id, this.nodes, this.attributes.build());
		}
	}
}
