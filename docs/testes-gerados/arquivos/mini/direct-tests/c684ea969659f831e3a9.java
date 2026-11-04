/*
 * @test
 * @summary Test java.util.regex.Pattern.matches(String, CharSequence) method
 * @run main PatternWitupTest
 */

import java.util.regex.Pattern;

public class PatternWitupTest {
    public static void main(String[] args) {
        // Basic match test: regex "a*b" matches "aaaaab"
        if (!Pattern.matches("a*b", "aaaaab")) {
            throw new AssertionError("Expected match for regex 'a*b' and input 'aaaaab'");
        }

        // Negative test: regex "a*b" does not match "aaaac"
        if (Pattern.matches("a*b", "aaaac")) {
            throw new AssertionError("Expected no match for regex 'a*b' and input 'aaaac'");
        }

        // Empty regex matches empty input
        if (!Pattern.matches("", "")) {
            throw new AssertionError("Expected match for empty regex and empty input");
        }

        // Regex matching empty string against non-empty input
        if (Pattern.matches("", "nonempty")) {
            throw new AssertionError("Expected no match for empty regex and non-empty input");
        }

        // Regex with special characters
        if (!Pattern.matches("\\d+", "12345")) {
            throw new AssertionError("Expected match for regex '\\d+' and input '12345'");
        }

        // Regex with special characters negative
        if (Pattern.matches("\\d+", "123a45")) {
            throw new AssertionError("Expected no match for regex '\\d+' and input '123a45'");
        }

        // Null input test - should throw NullPointerException
        try {
            Pattern.matches("a*b", null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // Expected
        }

        // Null regex test - should throw NullPointerException
        try {
            Pattern.matches(null, "input");
            throw new AssertionError("Expected NullPointerException for null regex");
        } catch (NullPointerException e) {
            // Expected
        }

        System.out.println("All tests passed.");
    }
}
