/*
 * @test
 * @summary Test the out() method of java.util.Formatter for correct Appendable return
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.io.StringBuilder;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testOutReturnsCorrectAppendable();
    }

    public static void testOutReturnsCorrectAppendable() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        Appendable result = formatter.out();
        if (result != sb) {
            throw new AssertionError("Expected Appendable to be the same as the one passed to Formatter constructor");
        }
        // Additional check: ensure that the returned Appendable is not null
        if (result == null) {
            throw new AssertionError("out() returned null");
        }
        // Check that the formatter is open and can be used
        formatter.format("Test: %d", 123);
        String output = sb.toString();
        if (!output.contains("Test: 123")) {
            throw new AssertionError("Formatter output does not contain expected formatted string");
        }
    }
}
