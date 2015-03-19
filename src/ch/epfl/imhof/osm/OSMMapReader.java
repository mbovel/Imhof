package ch.epfl.imhof.osm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 * @author Matteo Besançon (245826)
 *
 */
public final class OSMMapReader {

	private OSMMapReader() {
	}
	
	public static} OSMMAp readOSMFile(String fileName, boolean unGZip) throws IOException, SAXException{
		OSMMap.Builder mapBuilder = new OSMMap.Builder();
		
		try (InputStream i = new FileInputStream(fileName)) {
		      XMLReader r = XMLReaderFactory.createXMLReader();
		      }
		
	}
}

