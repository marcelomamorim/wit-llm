/*
 * @test
 * @summary Test getProperty method of ParserSAX class.
 * @run main ParserSAXWitupTest
 */

import jdk.internal.util.xml.impl.ParserSAX;

public class ParserSAXWitupTest {

    public static void main(String[] args) {
        testGetPropertyThrowsUnsupported();
        testGetPropertyWithNull();
        testGetPropertyWithEmptyString();
    }

    private static void testGetPropertyThrowsUnsupported() {
        ParserSAX parser = new ParserSAX();
        boolean threw = false;
        try {
            parser.getProperty("anyProperty");
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException to be thrown");
        }
    }

    private static void testGetPropertyWithNull() {
        ParserSAX parser = new ParserSAX();
        boolean threw = false;
        try {
            parser.getProperty(null);
        } catch (UnsupportedOperationException e) {
            threw = true;
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected exception to be thrown for null argument");
        }
    }

    private static void testGetPropertyWithEmptyString() {
        ParserSAX parser = new ParserSAX();
        boolean threw = false;
        try {
            parser.getProperty("");
        } catch (UnsupportedOperationException e) {
            threw = true;
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected exception to be thrown for empty string argument");
        }
    }
}
