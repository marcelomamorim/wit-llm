/*
 * @test
 * @summary Test Pattern.matches(String regex, CharSequence input) method
 * @run main PatternWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PatternWitupTest {
    public static void main(String[] args) {
        testSimpleMatch();
        testNoMatch();
        testEmptyRegex();
        testEmptyInput();
        testNullRegex();
        testNullInput();
    }

    private static void testSimpleMatch() {
        String regex = "a*b";
        String input = "aaaaab";
        boolean result = Pattern.matches(regex, input);
        if (!result) {
            throw new RuntimeException("Expected true for regex 'a*b' and input 'aaaaab'");
        }
    }

    private static void testNoMatch() {
        String regex = "a*b";
        String input = "aaaac";
        boolean result = Pattern.matches(regex, input);
        if (result) {
            throw new RuntimeException("Expected false for regex 'a*b' and input 'aaaac'");
        }
    }

    private static void testEmptyRegex() {
        String regex = "";
        String input = "";
        boolean result = Pattern.matches(regex, input);
        if (!result) {
            throw new RuntimeException("Expected true for empty regex and empty input");
        }
    }

    private static void testEmptyInput() {
        String regex = "a*";
        String input = "";
        boolean result = Pattern.matches(regex, input);
        if (!result) {
            throw new RuntimeException("Expected true for regex 'a*' and empty input");
        }
    }

    private static void testNullRegex() {
        try {
            Pattern.matches(null, "input");
            throw new RuntimeException("Expected NullPointerException for null regex");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testNullInput() {
        try {
            Pattern.matches("a*b", null);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
