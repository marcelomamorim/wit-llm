/*
 * @test
 * @summary Test setEntityResolver method of ParserSAX for null and valid inputs
 * @run main ParserSAXWitupTest
 */

import jdk.internal.util.xml.impl.ParserSAX;
import jdk.internal.org.xml.sax.EntityResolver;

public class ParserSAXWitupTest {

    public static void main(String[] args) {
        testSetEntityResolverNull();
        testSetEntityResolverValid();
    }

    public static void testSetEntityResolverNull() {
        ParserSAX parser = new ParserSAX();
        boolean exceptionThrown = false;
        try {
            parser.setEntityResolver(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when setting null EntityResolver");
        }
    }

    public static void testSetEntityResolverValid() {
        ParserSAX parser = new ParserSAX();
        EntityResolver resolver = new EntityResolver() {
            @Override
            public org.xml.sax.InputSource resolveEntity(String publicId, String systemId) {
                return null;
            }
        };
        parser.setEntityResolver(resolver);
        // No direct getter, so test that no exception is thrown and method completes
    }
}
