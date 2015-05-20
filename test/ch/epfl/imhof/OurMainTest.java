package ch.epfl.imhof;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class OurMainTest {
    
    @Test
    public void lausanne() throws IOException, SAXException {
        String[] args = { "test/data/big/lausanne.osm.gz",
                "test/data/big/N46E006.hgt", "6.5594", "46.5032", "6.6508", "46.5459", "300",
                "lausanne.png" };
        Main.main(args);
    }
}
