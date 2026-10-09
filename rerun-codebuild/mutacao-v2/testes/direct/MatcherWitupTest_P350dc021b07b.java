/*
 * @test
 * @summary Test java.util.regex.Matcher.appendReplacement behavior
 * @run main MatcherWitupTest_P350dc021b07b
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest_P350dc021b07b {
    public static void main(String[] args) {
        testAppendReplacementBasic();
        testAppendReplacementNoMatch();
    }

    private static void testAppendReplacementBasic() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("xxabyy");
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Expected to find a match");
        }
        StringBuilder sb = new StringBuilder();
        String replacement = "$2$1"; // swap groups
        m.appendReplacement(sb, replacement);
        // The result should be "xx" + "ba"
        String expected = "xxba";
        if (!sb.toString().equals(expected)) {
            throw new RuntimeException("appendReplacement result mismatch: expected '" + expected + "' but got '" + sb.toString() + "'");
        }
    }

    private static void testAppendReplacementNoMatch() {
        Pattern p = Pattern.compile("abc");
        Matcher m = p.matcher("def");
        StringBuilder sb = new StringBuilder();
        try {
            m.appendReplacement(sb, "xyz");
            throw new RuntimeException("Expected IllegalStateException when no match available");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
