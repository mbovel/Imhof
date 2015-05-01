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
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import ch.epfl.imhof.PointGeo;

/**
 * The <code>OSMMapReader</code> class is used to create an {@link OSMMap} given
 * an osm file. <code>OSMMapReader</code> is not instanciable.
 * 
 * @author Matthieu Bovel (250300)
 */
public final class OSMMapReader {
    private static final String NODE_EL       = "node";
    private static final String WAY_EL        = "way";
    private static final String REL_EL        = "relation";
    private static final String TAG_EL        = "tag";
    private static final String WAY_NODE_EL   = "nd";
    private static final String REL_MEMBER_EL = "member";
    private static final String LON_ATTR      = "lon";
    private static final String LAT_ATTR      = "lat";
    private static final String ID_ATTR       = "id";
    private static final String REF_ATTR      = "ref";
    private static final String ROLE_ATTR     = "role";
    private static final String TYPE_ATTR     = "type";
    private static final String KEY_ATTR      = "k";
    private static final String VALUE_ATTR    = "v";
    
    private OSMMapReader() {
    }
    
    /**
     * Reads an osm file and returns an {@link OSMMap}. Ungzip the file if the
     * second argument is <code>true</code>.
     * 
     * @param fileName
     *            the name of the osm file to read
     * @param unGZip
     *            boolean that tells if the file is gzip compressed or not.
     * @return an {@link OSMMap} based on the osm file in argument
     * @throws IOException
     *             when something goes wrong in the reading process
     * @throws when
     *             something is wrong with the osm file, for example when an
     *             attributes is missing
     */
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
        
        input.close();
        
        return handler.mapBuilder();
    }
    
    static private class XMLHandler extends DefaultHandler {
        private final OSMMap.Builder mapBuilder;
        private OSMNode.Builder      nodeBuilder;
        private OSMRelation.Builder  relBuilder;
        private OSMWay.Builder       wayBuilder;
        private Locator              locator;
        private Attributes           currentAtts;
        private String               currentEl;
        private State                state = State.IN_ROOT;
        
        public XMLHandler(OSMMap.Builder mapBuilder) {
            this.mapBuilder = mapBuilder;
        }
        
        protected OSMMap.Builder mapBuilder() {
            return mapBuilder;
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
                        case NODE_EL:
                            nodeBuilder = parseNodeEl();
                            state = State.IN_NODE;
                            break;
                        case WAY_EL:
                            wayBuilder = parseWayEl();
                            state = State.IN_WAY;
                            break;
                        case REL_EL:
                            relBuilder = parseRelationEl();
                            state = State.IN_RELATION;
                            break;
                    }
                    break;
                case IN_NODE:
                    switch (currentEl) {
                        case TAG_EL:
                            OSMAttr attr = parseTagEl();
                            nodeBuilder.setAttribute(attr.key(), attr.value());
                            break;
                    }
                    break;
                case IN_WAY:
                    switch (currentEl) {
                        case WAY_NODE_EL:
                            OSMNode node = parseNdEl();
                            if (node == null) {
                                wayBuilder.setIncomplete();
                            }
                            else {
                                wayBuilder.addNode(node);
                            }
                            break;
                        case TAG_EL:
                            OSMAttr attr = parseTagEl();
                            wayBuilder.setAttribute(attr.key(), attr.value());
                            break;
                    }
                    break;
                case IN_RELATION:
                    switch (currentEl) {
                        case REL_MEMBER_EL:
                            OSMRelation.Member member = parseMemberEl();
                            if (member == null) {
                                relBuilder.setIncomplete();
                            }
                            else {
                                relBuilder.addMember(member);
                            }
                            break;
                        case TAG_EL:
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
            
            // Incomplete elemnts are silently ignored
            switch (state) {
                case IN_ROOT:
                    break;
                case IN_NODE:
                    if (currentEl.equals(NODE_EL)) {
                        if (!nodeBuilder.isIncomplete()) {
                            mapBuilder.addNode(nodeBuilder.build());
                        }
                        
                        state = State.IN_ROOT;
                    }
                    break;
                case IN_WAY:
                    if (currentEl.equals(WAY_EL)) {
                        if (!wayBuilder.isIncomplete()) {
                            mapBuilder.addWay(wayBuilder.build());
                        }
                        
                        state = State.IN_ROOT;
                    }
                    break;
                case IN_RELATION:
                    if (currentEl.equals(REL_EL)) {
                        if (!relBuilder.isIncomplete()) {
                            mapBuilder.addRelation(relBuilder.build());
                        }
                        
                        state = State.IN_ROOT;
                    }
                    break;
            }
        }
        
        private OSMNode.Builder parseNodeEl() throws NumberFormatException,
                OSMMissingAttributeException {
            double lon = Math.toRadians(parseDoubleAttr(LON_ATTR));
            double lat = Math.toRadians(parseDoubleAttr(LAT_ATTR));
            PointGeo position = new PointGeo(lon, lat);
            
            return new OSMNode.Builder(parseLongAttr(ID_ATTR), position);
        }
        
        private OSMRelation.Builder parseRelationEl()
                throws NumberFormatException, OSMMissingAttributeException {
            return new OSMRelation.Builder(parseLongAttr(ID_ATTR));
        }
        
        private OSMWay.Builder parseWayEl() throws NumberFormatException,
                OSMMissingAttributeException {
            return new OSMWay.Builder(parseLongAttr(ID_ATTR));
        }
        
        private OSMNode parseNdEl() throws NumberFormatException,
                OSMMissingAttributeException {
            return mapBuilder.nodeForId(parseLongAttr(REF_ATTR));
        }
        
        private OSMRelation.Member parseMemberEl()
                throws OSMMissingAttributeException {
            OSMRelation.Member.Type type = null;
            String role = parseStringAttr(ROLE_ATTR);
            OSMEntity ref = null;
            long refId = parseLongAttr(REF_ATTR);
            
            switch (parseStringAttr(TYPE_ATTR)) {
                case NODE_EL:
                    type = OSMRelation.Member.Type.NODE;
                    ref = mapBuilder.nodeForId(refId);
                    break;
                case WAY_EL:
                    type = OSMRelation.Member.Type.WAY;
                    ref = mapBuilder.wayForId(refId);
                    break;
                case REL_EL:
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
            return new OSMAttr(
                parseStringAttr(KEY_ATTR),
                parseStringAttr(VALUE_ATTR));
        }
        
        private String parseStringAttr(String name)
                throws OSMMissingAttributeException {
            String str = currentAtts.getValue(name);
            
            if (str == null) {
                throw new OSMMissingAttributeException(currentEl, name, locator);
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
            private final String key;
            private final String value;
            
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
            IN_ROOT, IN_NODE, IN_WAY, IN_RELATION
        };
    }
    
    // This exception should be defined in its own file, but we put it here to
    // be sure it is not ignored by the « système de rendu ».
    
    /**
     * This exception is thrown if an attribute is missing on an element in an
     * osm file.
     * 
     * @author Matthieu Bovel (250300)
     *
     */
    public static class OSMMissingAttributeException extends SAXParseException {
        // What is a serialVersionUID and why should I use it?
        // http://stackoverflow.com/a/285809
        private static final long serialVersionUID = 1L;
        
        public OSMMissingAttributeException(String el, String attr,
                Locator locator) {
            super(
                "Missing attribute `" + attr + "` on element `" + el + "`",
                locator);
        }
    }
}
