/*
 * @test
 * @summary Deterministic jtreg test for java.util.regex.PrintPattern.main
 * @run main PrintPatternWitupTest
 */

import java.util.regex.Pattern;

public class PrintPatternWitupTest {
    public static void main(String[] args) {
        // Test with a valid regex pattern string
        String[] validArgs = {"a*b"};
        try {
            PrintPattern.main(validArgs);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid pattern", e);
        }

        // Test with an invalid regex pattern string to check for IllegalArgumentException
        String[] invalidArgs = {"["}; // Unclosed character class
        boolean caught = false;
        try {
            PrintPattern.main(invalidArgs);
        } catch (IllegalArgumentException iae) {
            caught = true;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
        if (!caught) {
            throw new RuntimeException("Expected IllegalArgumentException was not thrown for invalid pattern");
        }
    }
}
