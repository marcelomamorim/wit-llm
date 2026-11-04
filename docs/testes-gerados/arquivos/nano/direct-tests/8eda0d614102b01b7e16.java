/* @test    @summary Test for Matcher.appendTail method to verify correct appending of remaining text.    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testAppendTailWithRemainingText();
        testAppendTailWithNoRemainingText();
    }

    static void testAppendTailWithRemainingText() {
        String patternStr = "abc";
        Pattern pattern = Pattern.compile(patternStr);
        String input = "xyzabc123";
        Matcher matcher = pattern.matcher(input);
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match.");
        }
        StringBuffer sb = new StringBuffer();
        // Move matcher to position after 'abc'
        matcher.region(matcher.start(), matcher.end());
        // Append tail from current position
        StringBuffer result = matcher.appendTail(sb);
        String expected = "xyzabc123";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("appendTail result mismatch. Expected: '" + expected + "', but got: '" + result.toString() + "'");
        }
    }

    static void testAppendTailWithNoRemainingText() {
        String patternStr = "abc";
        Pattern pattern = Pattern.compile(patternStr);
        String input = "abc";
        Matcher matcher = pattern.matcher(input);
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match.");
        }
        StringBuffer sb = new StringBuffer();
        // Move matcher to position after the match
        matcher.region(matcher.end(), matcher.end());
        // Append tail from current position
        StringBuffer result = matcher.appendTail(sb);
        String expected = "";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("appendTail result mismatch. Expected empty string, but got: '" + result.toString() + "'");
        }
    }
}
