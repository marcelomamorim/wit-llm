/*
 * @test
 * @summary Tests for java.util.Formatter.format(String, Object...) method.
 * @run main FormatterWitupTest
 */

import java.util.Formatter;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testNullFormat();
        testEmptyFormat();
        testSimpleString();
        testNullArgument();
        testIndexOutOfBounds();
    }

    static void testNullFormat() {
        try {
            Formatter formatter = new Formatter();
            formatter.format(null, "test");
            System.out.println("testNullFormat: Failed - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testNullFormat: Passed");
        } catch (Exception e) {
            System.out.println("testNullFormat: Failed - Unexpected exception: " + e);
        }
    }

    static void testEmptyFormat() {
        try {
            Formatter formatter = new Formatter();
            formatter.format("");
            System.out.println("testEmptyFormat: Passed");
        } catch (Exception e) {
            System.out.println("testEmptyFormat: Failed - " + e);
        }
    }

    static void testSimpleString() {
        try {
            Formatter formatter = new Formatter();
            formatter.format("Hello, %s!", "World");
            System.out.println("testSimpleString: Passed");
        } catch (Exception e) {
            System.out.println("testSimpleString: Failed - " + e);
        }
    }

    static void testNullArgument() {
        try {
            Formatter formatter = new Formatter();
            formatter.format("Value: %s", (Object) null);
            System.out.println("testNullArgument: Passed");
        } catch (Exception e) {
            System.out.println("testNullArgument: Failed - " + e);
        }
    }

    static void testIndexOutOfBounds() {
        try {
            Formatter formatter = new Formatter();
            // Triggering IndexOutOfBoundsException by malformed format string
            formatter.format("%s", new Object[] {"a", "b"});
            System.out.println("testIndexOutOfBounds: Failed - Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("testIndexOutOfBounds: Passed");
        } catch (Exception e) {
            System.out.println("testIndexOutOfBounds: Failed - Unexpected exception: " + e);
        }
    }
}
