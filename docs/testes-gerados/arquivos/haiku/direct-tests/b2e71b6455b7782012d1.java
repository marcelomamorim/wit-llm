/*
 * @test
 * @summary Verifies that SAXParser.isXIncludeAware() throws UnsupportedOperationException by default
 * @modules java.base/jdk.internal.util.xml
 * @run main SAXParserWitupTest
 */

import jdk.internal.util.xml.SAXParser;

public class SAXParserWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    public static void main(String[] args) {
        SAXParser parser = new SAXParser() {};

        boolean thrownUnsupported = false;
        Throwable other = null;
        try {
            parser.isXIncludeAware();
        } catch (UnsupportedOperationException e) {
            thrownUnsupported = true;
        } catch (Throwable t) {
            other = t;
        }
        check(thrownUnsupported, "isXIncludeAware() should throw UnsupportedOperationException, got: " + other);

        boolean secondCallThrows = false;
        try {
            parser.isXIncludeAware();
        } catch (UnsupportedOperationException e) {
            secondCallThrows = true;
        }
        check(secondCallThrows, "repeated isXIncludeAware() call should also throw UnsupportedOperationException");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
