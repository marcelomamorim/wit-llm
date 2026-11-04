/* @test    @summary Test for Matcher.appendTail method behavior.    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testAppendTailWithRemainingText();
        testAppendTailWithEmptyText();
    }

    static void testAppendTailWithRemainingText() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcxyzabc");
        StringBuilder sb = new StringBuilder();
        if (matcher.find()) {
            // Append matched part
            sb.append("[MATCHED]");
            // Append tail
            matcher.appendTail(sb);
            // Expected: "[MATCHED]xyzabc"
            if (!sb.toString().equals("[MATCHED]xyzabc")) {
                throw new AssertionError("Failed: Expected '[MATCHED]xyzabc' but got '" + sb.toString() + "'");
            }
        } else {
            throw new AssertionError("Matcher did not find a match when expected.");
        }
    }

    static void testAppendTailWithEmptyText() {
        Pattern pattern = Pattern.compile("xyz");
        Matcher matcher = pattern.matcher("");
        StringBuilder sb = new StringBuilder();
        // No match expected
        if (matcher.find()) {
            throw new AssertionError("Matcher unexpectedly found a match.");
        }
        // Append tail should append remaining text (none in this case)
        matcher.appendTail(sb);
        // Expected: ""
        if (!sb.toString().equals("")) {
            throw new AssertionError("Failed: Expected empty string but got '" + sb.toString() + "'");
        }
    }
}