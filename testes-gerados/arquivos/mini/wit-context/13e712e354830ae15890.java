/*
 * @test
 * @summary Test java.util.regex.Matcher.replaceFirst(String) for null input and basic replacement
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testReplaceFirstNullReplacement();
        testReplaceFirstNoMatchReturnsOriginal();
        testReplaceFirstBasicReplacement();
    }

    private static void testReplaceFirstNullReplacement() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("abc");
        try {
            m.replaceFirst(null);
            throw new RuntimeException("Expected NullPointerException for null replacement");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testReplaceFirstNoMatchReturnsOriginal() {
        Pattern p = Pattern.compile("z");
        Matcher m = p.matcher("abc");
        String result = m.replaceFirst("X");
        if (!"abc".equals(result)) {
            throw new RuntimeException("Expected original string when no match found, got: " + result);
        }
    }

    private static void testReplaceFirstBasicReplacement() {
        Pattern p = Pattern.compile("b");
        Matcher m = p.matcher("abcabc");
        String result = m.replaceFirst("X");
        if (!"aXcabc".equals(result)) {
            throw new RuntimeException("Expected first 'b' replaced with 'X', got: " + result);
        }
    }
}
