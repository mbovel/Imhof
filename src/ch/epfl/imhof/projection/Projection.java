package ch.epfl.imhof.projection;
//ça ne marche pas sans les imports mais comme je suis une burne en portée je sais pas trop pourquoi ...
import ch.epfl.imhof.*;
import ch.epfl.imhof.geometry.*;
/*
 * je sais pas si j'ai bien fait de mettre la classe en abstract ...
 * y a rien de précisé dans la donnée !
 * De plus je crois que c'est un interface
 * */
public interface Projection {
	
	public Point project (PointGeo point);
	public PointGeo inverse (Point point);
}
