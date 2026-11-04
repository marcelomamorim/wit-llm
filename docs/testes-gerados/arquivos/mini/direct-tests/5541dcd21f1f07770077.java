/*
 * @test
 * @summary Test jdk.internal.util.xml.impl.ParserSAX.parse(InputSource) method
 * @run main ParserSAXWitupTest
 */

import java.io.IOException;
import jdk.internal.org.xml.sax.InputSource;
import jdk.internal.org.xml.sax.SAXException;
import jdk.internal.util.xml.impl.ParserSAX;

public class ParserSAXWitupTest {
    public static void main(String[] args) throws Exception {
        testNullInputSource();
        testValidInputSource();
    }

    private static void testNullInputSource() {
        ParserSAX parser = new ParserSAX();
        try {
            parser.parse(null);
            throw new RuntimeException("Expected IllegalArgumentException for null InputSource");
        } catch (IllegalArgumentException iae) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null InputSource", e);
        }
    }

    private static void testValidInputSource() throws Exception {
        ParserSAX parser = new ParserSAX();
        InputSource is = new InputSource();
        // No systemId or byteStream set, but InputSource is not null
        // We expect parse to run without throwing IllegalArgumentException
        // It may throw IOException or SAXException if parsing fails, but we catch and fail if unexpected
        try {
            parser.parse(is);
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException("Did not expect IllegalArgumentException for non-null InputSource", iae);
        } catch (IOException | SAXException e) {
            // Acceptable exceptions from parse
        } catch (RuntimeException rte) {
            // Acceptable if from parse
        }
    }
}
