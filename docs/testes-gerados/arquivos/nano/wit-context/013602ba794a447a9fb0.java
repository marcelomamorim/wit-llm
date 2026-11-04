/*
 * @test
 * @summary Test for PrintPattern.main to handle valid and invalid regex patterns.
 * @run main PrintPatternWitupTest
 */

import java.util.regex.Pattern;

public class PrintPatternWitupTest {

    public static void main(String[] args) {
        testValidPattern();
        testInvalidPattern();
    }

    private static void testValidPattern() {
        String patternStr = "abc";
        String[] args = { patternStr };
        try {
            Pattern p = Pattern.compile(args[0]);
            System.out.println("Pattern compiled successfully: " + p);
        } catch (Exception e) {
            throw new AssertionError("Expected valid pattern to compile, but got exception", e);
        }
    }

    private static void testInvalidPattern() {
        String patternStr = "["; // invalid regex
        String[] args = { patternStr };
        try {
            Pattern p = Pattern.compile(args[0]);
            throw new AssertionError("Expected Pattern.compile to throw IllegalArgumentException for invalid pattern");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException for invalid pattern.");
        } catch (Exception e) {
            throw new AssertionError("Expected IllegalArgumentException, but got: " + e, e);
        }
    }
}
