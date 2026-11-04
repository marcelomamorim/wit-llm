/*
 * @test
 * @summary Test for Matcher.replaceAll(Function<MatchResult, String>) method.
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithReplacer();
        testReplaceAllNoMatch();
        testReplaceAllNullReplacer();
    }

    static void testReplaceAllWithReplacer() {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher("abc 123 def 456");
        String result = matcher.replaceAll(mr -> {
            String matchStr = mr.group();
            int num = Integer.parseInt(matchStr);
            return String.valueOf(num * 2);
        });
        assert result.equals("abc 246 def 912") : "Expected 'abc 246 def 912', got: " + result;
    }

    static void testReplaceAllNoMatch() {
        Pattern pattern = Pattern.compile("xyz");
        Matcher matcher = pattern.matcher("abc 123 def 456");
        String result = matcher.replaceAll(mr -> "");
        assert result.equals("abc 123 def 456") : "Expected original string, got: " + result;
    }

    static void testReplaceAllNullReplacer() {
        Pattern pattern = Pattern.compile("a");
        Matcher matcher = pattern.matcher("abc");
        try {
            matcher.replaceAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
