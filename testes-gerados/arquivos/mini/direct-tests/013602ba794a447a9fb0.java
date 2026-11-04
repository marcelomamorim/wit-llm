/*
 * @test
 * @summary Test PrintPattern.main with various regex patterns
 * @run main java.util.regex.PrintPatternWitupTest
 */

import java.util.regex.Pattern;

public class PrintPatternWitupTest {

    public static void main(String[] args) {
        testPattern("a");
        testPattern("a*");
        testPattern("a+");
        testPattern("a{2,5}");
        testPattern("[a-zA-Z0-9]");
        testPattern("\\d{3}-\\d{2}-\\d{4}");
        testPattern("(abc|def)");
        testPattern("^start$");
        testPattern(".");
        testPattern("\\w+");
    }

    private static void testPattern(String regex) {
        System.out.println("Testing pattern: " + regex);
        // Call PrintPattern.main with single argument array
        java.util.regex.PrintPattern.main(new String[] { regex });
        System.out.println("---");
    }
}
