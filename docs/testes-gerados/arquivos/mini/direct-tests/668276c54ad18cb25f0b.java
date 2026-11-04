/*
 * @test
 * @summary Test ParserSAX.setEntityResolver behavior
 * @run main ParserSAXWitupTest
 */

import jdk.internal.util.xml.impl.ParserSAX;
import jdk.internal.org.xml.sax.EntityResolver;

public class ParserSAXWitupTest {
    public static void main(String[] args) throws Exception {
        ParserSAX parser = new ParserSAX();

        // Test that setting a valid EntityResolver updates the internal handler
        EntityResolver resolver = new EntityResolver() {
            // no methods needed for this test
        };
        parser.setEntityResolver(resolver);

        // There is no public getter for mHandEnt, but getContentHandler returns null if default
        // So we test that no exception is thrown and that setting null throws NullPointerException

        // Test that passing null throws NullPointerException
        try {
            parser.setEntityResolver(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
