/* @test    @summary Test for Matcher.replaceFirst method in java.util.regex package    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testReplaceFirstWithMatch();
        testReplaceFirstNoMatch();
        testReplaceFirstNullReplacer();
    }

    static void testReplaceFirstWithMatch() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foo bar foo");
        String result = matcher.replaceFirst(mr -> "baz");
        assert result.equals("baz bar foo") : "Expected 'baz bar foo', but got: " + result;
    }

    static void testReplaceFirstNoMatch() {
        Pattern pattern = Pattern.compile("xyz");
        Matcher matcher = pattern.matcher("abc def");
        String result = matcher.replaceFirst(mr -> "zzz");
        assert result.equals("abc def") : "Expected 'abc def', but got: " + result;
    }

    static void testReplaceFirstNullReplacer() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abc");
        try {
            matcher.replaceFirst(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
