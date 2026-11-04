/*
 * @test
 * @summary Test for setProperty method in ParserSAX class.
 * @run main ParserSAXWitupTest
 */

import jdk.internal.util.xml.impl.ParserSAX;

public class ParserSAXWitupTest {

    public static void main(String[] args) {
        testSetPropertyThrowsException();
    }

    public static void testSetPropertyThrowsException() {
        ParserSAX parser = new ParserSAX();
        try {
            parser.setProperty("anyProperty", null);
            // Fail if no exception is thrown
            System.err.println("Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("Caught expected UnsupportedOperationException.");
            System.exit(0);
        } catch (Throwable t) {
            // Unexpected exception, test fails
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
