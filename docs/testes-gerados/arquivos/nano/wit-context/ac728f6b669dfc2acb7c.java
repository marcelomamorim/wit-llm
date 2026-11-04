/*
 * @test
 * @summary Test for getProperty method in ParserSAX class.
 * @run main ParserSAXWitupTest
 */

import jdk.internal.util.xml.impl.ParserSAX;

public class ParserSAXWitupTest {

    public static void main(String[] args) {
        testGetPropertyThrowsException();
    }

    public static void testGetPropertyThrowsException() {
        ParserSAX parser = new ParserSAX();
        try {
            Object result = parser.getProperty("anyProperty");
            // Expecting an UnsupportedOperationException, so if no exception, test fails
            System.err.println("Expected UnsupportedOperationException, but got result: " + result);
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
            System.out.println("Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
