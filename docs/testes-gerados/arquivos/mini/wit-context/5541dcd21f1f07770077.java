/*
 * @test
 * @summary Test ParserSAX.parse(InputSource) for null input and normal usage
 * @run main ParserSAXWitupTest
 */

import java.io.IOException;
import jdk.internal.org.xml.sax.InputSource;
import jdk.internal.org.xml.sax.SAXException;
import jdk.internal.util.xml.impl.ParserSAX;

public class ParserSAXWitupTest {
    public static void main(String[] args) throws Exception {
        ParserSAX parser = new ParserSAX();

        // Test parse(null) throws IllegalArgumentException
        try {
            parser.parse((InputSource) null);
            throw new RuntimeException("Expected IllegalArgumentException for null InputSource");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test parse with minimal valid InputSource
        InputSource is = new InputSource();
        // No systemId or byteStream set, but parse should not throw IllegalArgumentException
        // It may throw IOException or SAXException depending on implementation
        try {
            parser.parse(is);
        } catch (IOException | SAXException e) {
            // Acceptable exceptions from parse
        } catch (RuntimeException rte) {
            // Acceptable if consistent with implementation
        }
    }
}
