/*
 * @test
 * @summary Test java.util.regex.Matcher.appendTail behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testAppendTailWithMatch();
        testAppendTailWithoutMatch();
        testAppendTailEmptyInput();
    }

    private static void testAppendTailWithMatch() {
        Pattern p = Pattern.compile("foo");
        Matcher m = p.matcher("foo bar foo");
        StringBuilder sb = new StringBuilder();
        boolean found = m.find();
        if (!found) throw new RuntimeException("Expected to find a match");
        sb.append(m.input().subSequence(0, m.end()));
        StringBuilder result = m.appendTail(sb);
        String expected = "foo bar foo";
        if (!result.toString().equals(expected)) {
            throw new RuntimeException("appendTail failed: expected '" + expected + "' but got '" + result + "'");
        }
    }

    private static void testAppendTailWithoutMatch() {
        Pattern p = Pattern.compile("xyz");
        Matcher m = p.matcher("abc def ghi");
        StringBuilder sb = new StringBuilder();
        // no match found, lastAppendPosition should be 0
        StringBuilder result = m.appendTail(sb);
        String expected = "abc def ghi";
        if (!result.toString().equals(expected)) {
            throw new RuntimeException("appendTail without match failed: expected '" + expected + "' but got '" + result + "'");
        }
    }

    private static void testAppendTailEmptyInput() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("");
        StringBuilder sb = new StringBuilder();
        StringBuilder result = m.appendTail(sb);
        String expected = "";
        if (!result.toString().equals(expected)) {
            throw new RuntimeException("appendTail with empty input failed: expected empty string but got '" + result + "'");
        }
    }
}
