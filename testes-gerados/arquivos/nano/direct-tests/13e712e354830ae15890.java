/* @test    @summary Test replaceFirst method of java.util.regex.Matcher    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testReplaceFirstWithMatch();
        testReplaceFirstNoMatch();
        testReplaceFirstNullReplacement();
    }

    static void testReplaceFirstWithMatch() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foo bar foo");
        String result = matcher.replaceFirst("baz");
        if (!"baz bar foo".equals(result)) {
            throw new AssertionError("Expected 'baz bar foo', but got: " + result);
        }
    }

    static void testReplaceFirstNoMatch() {
        Pattern pattern = Pattern.compile("xyz");
        Matcher matcher = pattern.matcher("abc def");
        String result = matcher.replaceFirst("zzz");
        if (!"abc def".equals(result)) {
            throw new AssertionError("Expected 'abc def', but got: " + result);
        }
    }

    static void testReplaceFirstNullReplacement() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foo bar");
        try {
            matcher.replaceFirst(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
