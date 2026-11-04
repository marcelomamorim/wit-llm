/*
 * @test
 * @summary Test setFeature method of ParserSAX class for supported features.
 * @run main ParserSAXWitupTest
 */

import jdk.internal.util.xml.impl.ParserSAX;

public class ParserSAXWitupTest {

    public static void main(String[] args) {
        testSetFeatureThrowsUnsupported();
    }

    public static void testSetFeatureThrowsUnsupported() {
        ParserSAX parser = new ParserSAX();
        boolean exceptionThrown = false;
        try {
            parser.setFeature("http://xml.org/sax/features/namespaces", true);
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
        exceptionThrown = false;
        try {
            parser.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
