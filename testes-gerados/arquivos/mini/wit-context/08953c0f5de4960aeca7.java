/*
 * @test
 * @summary Test java.util.Formatter.out() method behavior including FormatterClosedException
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.util.FormatterClosedException;
import java.io.IOException;
import java.lang.Appendable;

public class FormatterWitupTest {
    public static void main(String[] args) throws Exception {
        testOutReturnsAppendable();
        testOutThrowsFormatterClosedException();
        System.out.println("FormatterWitupTest passed.");
    }

    private static void testOutReturnsAppendable() throws IOException {
        // Create a Formatter with a StringBuilder as Appendable
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        // out() should return the same Appendable passed in constructor
        Appendable out = formatter.out();
        if (out != sb) {
            throw new AssertionError("Formatter.out() did not return the expected Appendable instance");
        }

        // Also test that formatting works and appends to sb
        formatter.format("%s", "test");
        if (!"test".contentEquals(sb)) {
            throw new AssertionError("Formatted output not appended correctly");
        }

        formatter.close();
    }

    private static void testOutThrowsFormatterClosedException() {
        // Create a Formatter and close it to cause FormatterClosedException on out()
        Formatter formatter = new Formatter();
        formatter.close();

        boolean thrown = false;
        try {
            formatter.out();
        } catch (FormatterClosedException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Formatter.out() did not throw FormatterClosedException after close()");
        }
    }
}
