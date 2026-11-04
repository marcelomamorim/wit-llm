/*
 * @test
 * @summary Test setProperty method of ParserSAX class for supported and unsupported properties
 * @run main ParserSAXWitupTest
 */

import jdk.internal.util.xml.impl.ParserSAX;

public class ParserSAXWitupTest {

    public static void main(String[] args) {
        testSetPropertyThrowsUnsupported();
        testSetPropertyWithNull();
    }

    private static void testSetPropertyThrowsUnsupported() {
        ParserSAX parser = new ParserSAX();
        boolean threwException = false;
        try {
            parser.setProperty("anyProperty", new Object());
        } catch (UnsupportedOperationException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }

    private static void testSetPropertyWithNull() {
        ParserSAX parser = new ParserSAX();
        boolean threwException = false;
        try {
            parser.setProperty(null, null);
        } catch (UnsupportedOperationException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown for null inputs.");
        }
    }
}
