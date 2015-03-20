package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
     * @author Matteo Besançon (245826)
     *
     */
    public static class Builder {
        private List<OSMWay> ways;
        private List<OSMRelation> relations;
        private List<OSMNode> nodes = new ArrayList<OSMNode>();

        /**
         * Adds a new node to the future <code>OSMMap</code>.
         * 
         * @param newNode
         *            the future <code>OSMMap</code>'s node.
         */
        public void addNode(OSMNode newNode) {
            nodes.add(newNode);
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
            for (OSMNode osmNode : nodes) {
                System.out.println(id);
                if (id == osmNode.id()) {
                    return osmNode;
                }
            }
            return null;
        }

        /**
         * Adds a new way to the future <code>OSMMap</code>.
         * 
         * @param newWay
         *            the future <code>OSMMap</code>'s way.
         */
        public void addWay(OSMWay newWay) {
            ways.add(newWay);
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
            for (OSMWay osmWay : ways) {
                if (id == osmWay.id()) {
                    return osmWay;
                }
            }
            return null;
        }

        /**
         * Adds a new relation to the future <code>OSMMap</code>.
         * 
         * @param newrelation
         *            the future <code>OSMMap</code>'s relation.
         */
        public void addRelation(OSMRelation newrelation) {
            relations.add(newrelation);
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
        public OSMRelation relatiuonForId(long id) {
            for (OSMRelation osmRelation : relations) {
                if (id == osmRelation.id()) {
                    return osmRelation;
                }
            }
            return null;
        }

        /**
         * Returns a new <code>OSMMap</code> instance using the datas provided
         * by the <code>OSMMap.Builder</code>.
         * 
         * @return the new <code>OSMMap</code>.
         */
        public OSMMap build() {
            return new OSMMap(this.ways, this.relations);
        }
    }
}
