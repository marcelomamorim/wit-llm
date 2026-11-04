/*
 * @test
 * @summary Test ParserSAX.setEntityResolver for null argument and normal usage
 * @run main ParserSAXWitupTest
 */

import jdk.internal.util.xml.impl.ParserSAX;
import jdk.internal.org.xml.sax.EntityResolver;

public class ParserSAXWitupTest {
    public static void main(String[] args) {
        ParserSAX parser = new ParserSAX();

        // Test that setEntityResolver throws NullPointerException when passed null
        try {
            parser.setEntityResolver(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }

        // Test that setEntityResolver accepts a non-null EntityResolver
        EntityResolver dummyResolver = new EntityResolver() {
            // No methods need to be implemented for this test
        };
        parser.setEntityResolver(dummyResolver);

        // Verify that getContentHandler returns null since no content handler set explicitly
        // (This is indirect but no direct getter for EntityResolver)
        if (parser.getContentHandler() != null) {
            throw new RuntimeException("Expected content handler to be null by default");
        }
    }
}
