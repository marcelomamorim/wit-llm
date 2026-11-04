/*
 * @test
 * @summary Test java.util.regex.Matcher.appendReplacement behavior and exceptions
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testAppendReplacementNoMatch();
        testAppendReplacementNormal();
    }

    private static void testAppendReplacementNoMatch() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("");
        StringBuffer sb = new StringBuffer();
        try {
            m.appendReplacement(sb, "x");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // Expected exception because no match was attempted
        }
    }

    private static void testAppendReplacementNormal() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("aba");
        StringBuffer sb = new StringBuffer();
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Setup failure: expected to find a match");
        }
        m.appendReplacement(sb, "x");
        // After appendReplacement, sb should contain the text before the match (empty) plus replacement "x"
        if (!sb.toString().equals("x")) {
            throw new RuntimeException("appendReplacement did not produce expected output: " + sb);
        }
        // Append tail to complete the replacement
        m.appendTail(sb);
        // The original string is "aba", first match "a" replaced by "x", so result should be "xba"
        if (!sb.toString().equals("xba")) {
            throw new RuntimeException("appendTail did not produce expected output: " + sb);
        }
    }
}
