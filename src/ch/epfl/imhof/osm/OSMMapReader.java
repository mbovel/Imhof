package ch.epfl.imhof.osm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

//import ch.epfl.imhof.PointGeo;
//import ch.epfl.imhof.osm.OSMMap.Builder;

/**
 * @author Matthieu Bovel (250300)
 */
public final class OSMMapReader {

    private OSMMapReader() {
    }

    public static OSMMap readOSMFile(String fileName, boolean unGZip)
            throws IOException, SAXException {
        return readOSMFileToBuilder(fileName, unGZip).build();

    }

    public static OSMMap.Builder readOSMFileToBuilder(String fileName, boolean unGZip)
            throws SAXException, IOException {
        InputStream input = new FileInputStream(fileName);
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        State state = State.IN_ROOT;
        OSMMap.Builder mapBuilder = new OSMMap.Builder();
        //OSMWay.Builder wayBuilder;
        //OSMRelation.Builder relationBuilder;

        if (unGZip) {
            input = new GZIPInputStream(input);
        }

        xmlReader.setContentHandler(new DefaultHandler() {
            @Override
            public void startElement(String uri, String lName, String qName,
                    Attributes atts) throws SAXException {
                switch (state) {
                case IN_ROOT:
                    switch (qName) {
                    case "node":
//                        long id = Long.parseLong(atts.getValue("id"));
//                        PointGeo position = new PointGeo(Double.parseDouble(atts.getValue("long")), Double.parseDouble(atts.getValue("lat")));
//                        ch.epfl.imhof.Attributes nodeAtts =  new ch.epfl.imhof.Attributes(new HashMap<String, String>());
//                        mapBuilder.addNode(new OSMNode(id, position, nodeAtts));
                        break;
                    case "relation":
                        break;
                    case "way":
                        break;
                    }
                    break;
                case IN_WAY:
                    switch (qName) {
                    case "nd":
                        break;
                    case "tag":
                        break;
                    }
                    break;
                case IN_RELATION:
                    switch (qName) {
                    case "member":
                        break;
                    case "tag":
                        break;
                    }
                    break;
                }
            }

            @Override
            public void endElement(String uri, String lName, String qName) {
                switch (state) {
                case IN_ROOT:
                    break;
                case IN_WAY:
                    if (qName == "way") {

                    }
                    break;
                case IN_RELATION:
                    if (qName == "relation") {

                    }
                    break;
                }
            }
        });

        xmlReader.parse(new InputSource(input));

        return mapBuilder;
    }

    private enum State {
        IN_ROOT, IN_WAY, IN_RELATION
    };
}
