package ch.epfl.imhof;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.testUtils.OurTestsUtils;

public class OurMainTest {
    @Test
    public void lausanne() throws IOException, SAXException {
        testMain("test/data/big/lausanne.osm.gz",
                "test/data/big/N46E006.hgt", "6.5594", "46.5032", "6.6508",
                "46.5459", "300", "lausanne.png");
    }
    
    @Test
    public void interlaken() throws IOException, SAXException {
        testMain("test/data/big/interlaken.osm.gz",
                "test/data/big/N46E007.hgt", "7.8122", "46.6645", "7.9049",
                "46.7061", "300", "interlaken.png");
    }
    
    @Test
    public void berne() throws IOException, SAXException {
        testMain("test/data/big/berne.osm.gz",
                "test/data/big/N46E007.hgt", "7.3912", "46.9322", "7.4841",
                "46.9742", "300", "berne.png");
    }
    
    private static void testMain(String... args) throws IOException, SAXException {
        Main.main(args);
        File result = new File(args[7]);
        OurTestsUtils.assertImagesSame("test/data/big/" + args[7], ImageIO.read(result));
        result.delete();
    }
}
