/*
 * @test
 * @summary Test java.util.regex.Matcher.appendTail behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testAppendTailAfterFind();
        testAppendTailWithoutFind();
        testAppendTailEmptyInput();
    }

    private static void testAppendTailAfterFind() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("abc");
        boolean found = m.find();
        if (!found) throw new RuntimeException("Expected to find a match");
        StringBuffer sb = new StringBuffer();
        sb.append("prefix-");
        StringBuffer result = m.appendTail(sb);
        if (result != sb) throw new RuntimeException("appendTail should return the same StringBuffer instance");
        String expected = "prefix-bc";
        if (!sb.toString().equals(expected)) {
            throw new RuntimeException("appendTail result mismatch: expected '" + expected + "' but got '" + sb.toString() + "'");
        }
    }

    private static void testAppendTailWithoutFind() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("xyz");
        // no find called, so lastAppendPosition should be 0
        StringBuffer sb = new StringBuffer();
        sb.append("start-");
        StringBuffer result = m.appendTail(sb);
        if (result != sb) throw new RuntimeException("appendTail should return the same StringBuffer instance");
        String expected = "start-xyz";
        if (!sb.toString().equals(expected)) {
            throw new RuntimeException("appendTail result mismatch without find: expected '" + expected + "' but got '" + sb.toString() + "'");
        }
    }

    private static void testAppendTailEmptyInput() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("");
        StringBuffer sb = new StringBuffer();
        sb.append("empty-");
        StringBuffer result = m.appendTail(sb);
        if (result != sb) throw new RuntimeException("appendTail should return the same StringBuffer instance");
        String expected = "empty-";
        if (!sb.toString().equals(expected)) {
            throw new RuntimeException("appendTail result mismatch with empty input: expected '" + expected + "' but got '" + sb.toString() + "'");
        }
    }
}
