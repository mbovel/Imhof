package ch.epfl.imhof.osm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import ch.epfl.imhof.PointGeo;

/**
 * @author Matthieu Bovel (250300)
 */
public final class OSMMapReader {
    private OSMMapReader() {
    }
    
    public static OSMMap readOSMFile(String fileName, boolean unGZip)
            throws IOException, SAXException {
        OSMMap.Builder mapBuilder = new OSMMap.Builder();
        readOSMFileToBuilder(fileName, unGZip, mapBuilder);
        return mapBuilder.build();
    }
    
    private static OSMMap.Builder readOSMFileToBuilder(String fileName,
            boolean unGZip, OSMMap.Builder mapBuilder) throws SAXException,
            IOException {
        InputStream input = new FileInputStream(fileName);
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        XMLHandler handler = new XMLHandler(mapBuilder);
        
        if (unGZip) {
            input = new GZIPInputStream(input);
        }
        
        xmlReader.setContentHandler(handler);
        
        xmlReader.parse(new InputSource(input));
        
        return handler.mapBuilder();
    }
    
    static private class XMLHandler extends DefaultHandler {
        private final OSMMap.Builder mapBuilder;
        OSMNode.Builder              nodeBuilder;
        OSMRelation.Builder          relBuilder;
        OSMWay.Builder               wayBuilder;
        private Locator              locator;
        private Attributes           currentAtts;
        private String               currentEl;
        private State                state = State.IN_ROOT;
        
        public XMLHandler(OSMMap.Builder mapBuilder) {
            this.mapBuilder = mapBuilder;
        }
        
        protected OSMMap.Builder mapBuilder() {
            return this.mapBuilder;
        }
        
        @Override
        public void setDocumentLocator(Locator locator) {
            this.locator = locator;
        }
        
        @Override
        public void startElement(String uri, String lName, String qName,
                Attributes atts) throws SAXException {
            currentAtts = atts;
            currentEl = qName;
            
            switch (state) {
                case IN_ROOT:
                    switch (currentEl) {
                        case "node":
                            nodeBuilder = parseNodeEl();
                            break;
                        case "way":
                            wayBuilder = parseWayEl();
                            this.state = State.IN_WAY;
                            break;
                        case "relation":
                            relBuilder = parseRelationEl();
                            this.state = State.IN_RELATION;
                            break;
                    }
                    break;
                case IN_WAY:
                    switch (currentEl) {
                        case "nd":
                            OSMNode node = parseNdEl();
                            if (node == null) {
                                wayBuilder.setIncomplete();
                            }
                            else {
                                wayBuilder.addNode(node);
                            }
                            break;
                        case "tag":
                            OSMAttr attr = parseTagEl();
                            wayBuilder.setAttribute(attr.key(), attr.value());
                            break;
                    }
                    break;
                case IN_RELATION:
                    switch (currentEl) {
                        case "member":
                            OSMRelation.Member member = parseMemberEl();
                            if (member == null) {
                                relBuilder.setIncomplete();
                            }
                            else {
                                relBuilder.addMember(member);
                            }
                            break;
                        case "tag":
                            OSMAttr attr = parseTagEl();
                            relBuilder.setAttribute(attr.key(), attr.value());
                            break;
                    }
                    break;
            }
        }
        
        @Override
        public void endElement(String uri, String lName, String qName) {
            currentEl = qName;
            switch (state) {
                case IN_ROOT:
                    if (currentEl.equals("node")) {
                        mapBuilder.addNode(nodeBuilder.build());
                    }
                    break;
                case IN_WAY:
                    // Incomplete Way is silently ignored
                    if (currentEl.equals("way") && !wayBuilder.isIncomplete()) {
                        mapBuilder.addWay(wayBuilder.build());
                        state = State.IN_ROOT;
                    }
                    break;
                case IN_RELATION:
                    // Incomplete Relation is silently ignored
                    if (currentEl.equals("relation")
                            && !relBuilder.isIncomplete()) {
                        mapBuilder.addRelation(relBuilder.build());
                        state = State.IN_ROOT;
                    }
                    break;
            }
        }
        
        private OSMNode.Builder parseNodeEl() throws NumberFormatException,
                OSMMissingAttributeException {
            double lon = Math.toRadians(parseDoubleAttr("lon"));
            double lat = Math.toRadians(parseDoubleAttr("lat"));
            PointGeo position = new PointGeo(lon, lat);
            
            return new OSMNode.Builder(parseLongAttr("id"), position);
        }
        
        private OSMRelation.Builder parseRelationEl()
                throws NumberFormatException, OSMMissingAttributeException {
            return new OSMRelation.Builder(parseLongAttr("id"));
        }
        
        private OSMWay.Builder parseWayEl() throws NumberFormatException,
                OSMMissingAttributeException {
            return new OSMWay.Builder(parseLongAttr("id"));
        }
        
        private OSMNode parseNdEl() throws NumberFormatException,
                OSMMissingAttributeException {
            return mapBuilder.nodeForId(parseLongAttr("ref"));
        }
        
        private OSMRelation.Member parseMemberEl()
                throws OSMMissingAttributeException {
            OSMRelation.Member.Type type = null;
            String role = parseStringAttr("role");
            OSMEntity ref = null;
            long refId = parseLongAttr("ref");
            
            switch (parseStringAttr("type")) {
                case "node":
                    type = OSMRelation.Member.Type.NODE;
                    ref = mapBuilder.nodeForId(refId);
                    break;
                case "way":
                    type = OSMRelation.Member.Type.WAY;
                    ref = mapBuilder.wayForId(refId);
                    break;
                case "relation":
                    type = OSMRelation.Member.Type.RELATION;
                    ref = mapBuilder.relationForId(refId);
                    break;
            }
            
            if (ref == null) {
                return null;
            }
            
            return new OSMRelation.Member(type, role, ref);
        }
        
        private OSMAttr parseTagEl() throws OSMMissingAttributeException {
            return new OSMAttr(parseStringAttr("k"), parseStringAttr("v"));
        }
        
        private String parseStringAttr(String name)
                throws OSMMissingAttributeException {
            String str = currentAtts.getValue(name);
            
            if (str == null) {
                throw new OSMMissingAttributeException(currentEl, "id", locator);
            }
            
            return str;
        }
        
        private long parseLongAttr(String name) throws NumberFormatException,
                OSMMissingAttributeException {
            return Long.parseLong(parseStringAttr(name));
        }
        
        private double parseDoubleAttr(String name)
                throws NumberFormatException, OSMMissingAttributeException {
            return Double.parseDouble(parseStringAttr(name));
        }
        
        private class OSMAttr {
            private String key;
            private String value;
            
            OSMAttr(String key, String value) {
                this.key = key;
                this.value = value;
            }
            
            public String key() {
                return key;
            }
            
            public String value() {
                return value;
            }
        }
        
        private enum State {
            IN_ROOT, IN_WAY, IN_RELATION
        };
    }
}
