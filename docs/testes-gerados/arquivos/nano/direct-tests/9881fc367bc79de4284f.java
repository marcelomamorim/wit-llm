/*
 * @test
 * @summary Test the toString() method of java.util.Formatter for correct output.
 * @run main FormatterWitupTest
 */

import java.util.Formatter;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testToStringReturnsExpectedString();
    }

    public static void testToStringReturnsExpectedString() {
        Formatter formatter = new Formatter();
        String result = formatter.toString();
        String expected = ""; // Expecting empty string as no data has been formatted
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: '" + expected + "', but got: '" + result + "'");
        }
    }
}
