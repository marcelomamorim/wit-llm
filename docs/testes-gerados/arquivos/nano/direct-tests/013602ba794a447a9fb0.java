/*
 * @test
 * @summary Test PrintPattern.main with various regex patterns
 * @run main PrintPatternWitupTest
 */

import java.util.regex.Pattern;

public class PrintPatternWitupTest {

    public static void main(String[] args) {
        testPattern("a*");
        testPattern("[a-z]+\d?");
        testPattern("\\d{3,5}");
        testPattern("^hello$");
        testPattern("(foo|bar)?");
    }

    private static void testPattern(String patternStr) {
        try {
            String[] args = { patternStr };
            java.util.regex.PrintPattern.main(args);
        } catch (Exception e) {
            System.err.println("Error testing pattern: " + patternStr + ": " + e);
        }
    }
}
