/*
 * @test
 * @summary Test java.util.regex.Matcher.replaceFirst(String) method
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testReplaceFirstNullReplacement();
        testReplaceFirstNoMatch();
        testReplaceFirstBasicReplacement();
    }

    private static void testReplaceFirstNullReplacement() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("abc");
        try {
            m.replaceFirst(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            if (!"replacement".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch");
            }
        }
    }

    private static void testReplaceFirstNoMatch() {
        Pattern p = Pattern.compile("z");
        Matcher m = p.matcher("abc");
        String result = m.replaceFirst("X");
        if (!"abc".equals(result)) {
            throw new RuntimeException("Expected unchanged string when no match, got: " + result);
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
