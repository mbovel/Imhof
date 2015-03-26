package ch.epfl.imhof.osm;

import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

// How to define custom exception class in Java, the easiest way?
// http://stackoverflow.com/questions/3776327/how-to-define-custom-exception-class-in-java-the-easiest-way
/**
 * @author Matthieu Bovel (250300)
 */
public class OSMMissingAttributeException extends SAXParseException {
    // What is a serialVersionUID and why should I use it?
    // http://stackoverflow.com/a/285809
    private static final long serialVersionUID = 1L;

    public OSMMissingAttributeException(String el, String attr, Locator locator) {
        super("Missing attribute `" + attr + "` on element `" + el +  "`", locator);
    }
}
