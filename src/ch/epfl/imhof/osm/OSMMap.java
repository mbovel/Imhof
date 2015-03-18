package ch.epfl.imhof.osm;

import java.util.Collection;
import java.util.List;

/**
 * @author Matteo Besançon (245826)
 *
 */
public final class OSMMap {
	private final List<OSMWay> ways;
	private final List<OSMRelation> relations;

	public OSMMap(Collection<OSMWay> ways, Collection<OSMRelation> relations) {
		this.relations = (List<OSMRelation>) relations;
		this.ways = (List<OSMWay>) ways;
	}
	
	public List<OSMWay> ways(){
		return ways;
	}
	
	public List<OSMRelation> relations(){
		return relations;
	}
	
	public static class Builder{
		private List<OSMWay> ways;
		private List<OSMRelation> relations;
		private List<OSMNode> nodes;
		
		public void addNode (OSMNode newnode){
			nodes.add(newnode);
		}
		
		public OSMNode nodeForId (long id){
			for (OSMNode osmNode : nodes) {
				if (id == osmNode.id()){
					return osmNode;
				}
			}
			return null;
		}
		
		public void addWay (OSMWay newWay){
			ways.add(newWay);
		}
		
		public OSMWay wayForId (long id){
			for (OSMWay osmWay : ways) {
				if (id == osmWay.id()){
					return osmWay;
				}
			}
			return null;
		}
		
		public void addRelation (OSMRelation newrelation){
			relations.add(newrelation);
		}
		
		public OSMRelation relatiuonForId (long id){
			for (OSMRelation osmRelation : relations){
				if (id == osmRelation.id()){
					return osmRelation;
				}
			}
			return null;
		}
		
		public OSMMap build(){
			return new OSMMap(this.ways, this.relations);
		}
	}
}
