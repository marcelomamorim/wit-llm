/*
 * @test
 * @summary Test java.util.Formatter.toString() for expected behavior and exception
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.util.FormatterClosedException;

public class FormatterWitupTest {
    public static void main(String[] args) throws Exception {
        testToStringNormal();
        testToStringClosedException();
    }

    private static void testToStringNormal() {
        Formatter f = new Formatter();
        String s = f.toString();
        if (s == null) {
            throw new AssertionError("toString() returned null");
        }
        // The toString() of Formatter delegates to the Appendable's toString(),
        // default Formatter uses StringBuilder, so expect non-empty string
        if (s.length() < 0) {
            throw new AssertionError("toString() returned empty string");
        }
        f.close();
    }

    private static void testToStringClosedException() {
        Formatter f = new Formatter();
        f.close();
        try {
            f.toString();
            throw new AssertionError("Expected FormatterClosedException not thrown");
        } catch (FormatterClosedException ex) {
            // Expected exception
        }
    }
}
