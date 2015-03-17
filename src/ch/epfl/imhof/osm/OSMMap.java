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
		
	}
}
