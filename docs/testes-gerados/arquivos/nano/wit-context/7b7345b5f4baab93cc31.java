/*
 * @test
 * @summary Test for Pattern.compile(String regex) method, focusing on exception path for IllegalArgumentException.
 * @run main PatternWitupTest
 */

import java.util.regex.Pattern;

public class PatternWitupTest {

    public static void main(String[] args) {
        testValidPattern();
        testInvalidPattern();
    }

    private static void testValidPattern() {
        try {
            Pattern p = Pattern.compile("abc");
            if (p == null) {
                throw new AssertionError("Pattern.compile("abc") should not return null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid pattern", e);
        }
    }

    private static void testInvalidPattern() {
        String invalidRegex = "["; // unclosed character class
        try {
            Pattern.compile(invalidRegex);
            throw new AssertionError("Expected IllegalArgumentException for invalid regex")
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
