/*
 * @test
 * @summary Test java.util.Formatter.out() method
 * @run main java.util.FormatterWitupTest
 */

import java.util.Formatter;
import java.io.IOException;
import java.lang.Appendable;

public class FormatterWitupTest {
    public static void main(String[] args) throws IOException {
        testOutReturnsAppendable();
        testOutThrowsAfterClose();
    }

    private static void testOutReturnsAppendable() {
        Formatter formatter = new Formatter();
        Appendable out = formatter.out();
        if (out == null) {
            throw new AssertionError("Formatter.out() returned null");
        }
        // The returned Appendable should be the same as the one passed or default
        // We cannot assert exact type, but we can append to it and check no exception
        try {
            out.append('a');
        } catch (IOException e) {
            throw new AssertionError("Appendable from Formatter.out() threw IOException", e);
        }
        formatter.close();
    }

    private static void testOutThrowsAfterClose() {
        Formatter formatter = new Formatter();
        formatter.close();
        try {
            formatter.out();
            throw new AssertionError("Formatter.out() did not throw after close");
        } catch (IllegalStateException ise) {
            // Expected exception
        }
    }
}
