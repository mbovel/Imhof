package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents an open street map Map.
 * <p>
 * An open street map Map has some ways ({@link OSMWay}) and some relations (
 * {@link OSMRelation}).
 * 
 * @author Matteo Besançon (245826)
 *
 */
public final class OSMMap {
    private final List<OSMWay> ways;
    private final List<OSMRelation> relations;

    /**
     * Constructs a new <code>OSMMap</code> given its ways and its relations.
     * 
     * @param ways
     *            <code>OSMMap</code>'s ways ({@link List} of {@link OSMWay}).
     * @param relations
     *            <code>OSMMap</code>'s relations ({@link List} of
     *            {@link OSMRelation}).
     */
    public OSMMap(Collection<OSMWay> ways, Collection<OSMRelation> relations) {
        this.ways = Collections.unmodifiableList(new ArrayList<OSMWay>(ways));
        this.relations = Collections
                .unmodifiableList(new ArrayList<OSMRelation>(relations));
    }

    /**
     * Returns the ways of the <code>OSMMap</code>.
     * 
     * @return the ways of the <code>OSMMap</code>.
     */
    public List<OSMWay> ways() {
        return ways;
    }

    /**
     * Returns the relations of the <code>OSMMap</code>.
     * 
     * @return the relations of the <code>OSMMap</code>.
     */
    public List<OSMRelation> relations() {
        return relations;
    }

    /**
     * A class that helps in the creation of an {@link OSMMap}.
     * 
     * @author Matthieu Bovel (250300)
     * @author Matteo Besançon (245826)
     *
     */
    public static class Builder {
        private Map<Long, OSMNode> nodes = new HashMap<Long, OSMNode>();
        private Map<Long, OSMWay> ways = new HashMap<Long, OSMWay>();
        private Map<Long, OSMRelation> relations = new HashMap<Long, OSMRelation>();

        /**
         * Adds a new node to the future <code>OSMMap</code>.
         * 
         * @param node
         *            a node to add to the future <code>OSMMap</code>.
         */
        public void addNode(OSMNode node) {
            nodes.put(node.id(), node);
        }

        /**
         * Returns a node of the future <code>OSMMap</code> given its
         * identification or <code>null</code> if no node corresponds to the
         * given id.
         * 
         * @param id
         *            the wanted node's identifiction (<code>long</code>).
         * 
         * @return the node of the future <code>OSMMap</code> given its
         *         identification or <code>null</code> if any node corresponds
         *         to the given id.
         */
        public OSMNode nodeForId(long id) {
            return nodes.get(id);
        }

        /**
         * Adds a new way to the future <code>OSMMap</code>.
         * 
         * @param newWay
         *            the future <code>OSMMap</code>'s way.
         */
        public void addWay(OSMWay way) {
            ways.put(way.id(), way);
        }

        /**
         * Returns a way of the future <code>OSMMap</code> given its
         * identification or <code>null</code> if no way corresponds to the
         * given id.
         * 
         * @param id
         *            the wanted way's identification (<code>long</code>).
         * 
         * @return the way of the future <code>OSMMap</code> given its
         *         identification or <code>null</code> if no way corresponds to
         *         the given id.
         */
        public OSMWay wayForId(long id) {
            return ways.get(id);
        }

        /**
         * Adds a new relation to the future <code>OSMMap</code>.
         * 
         * @param relation
         *            the future <code>OSMMap</code>'s relation.
         */
        public void addRelation(OSMRelation relation) {
            relations.put(relation.id(), relation);
        }

        /**
         * Returns a relation of the future <code>OSMMap</code> given its
         * identification or <code>null</code> if no relation corresponds to the
         * given id.
         * 
         * @param id
         *            the wanted relations's identification (<code>long</code>).
         * 
         * @return the relation of the future <code>OSMMap</code> given its
         *         identification or <code>null</code> if no relation
         *         corresponds to the given id.
         */
        public OSMRelation relationForId(long id) {
            return relations.get(id);
        }

        /**
         * Returns a new <code>OSMMap</code> instance using the datas provided
         * by the <code>OSMMap.Builder</code>.
         * 
         * @return the new <code>OSMMap</code>.
         */
        public OSMMap build() {
            return new OSMMap(this.ways.values(), this.relations.values());
        }
    }
}
