/*
 * @test
 * @summary Test java.util.regex.Matcher.appendReplacement behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
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
        StringBuffer sb = new StringBuffer();
        m.appendReplacement(sb, "$2$1"); // swap groups
        // The result should be "xxba" because "ab" matched and replaced by "ba"
        // and the prefix "xx" before the match is appended
        String expected = "xxba";
        if (!sb.toString().equals(expected)) {
            throw new RuntimeException("appendReplacement result mismatch: expected '" + expected + "' but got '" + sb.toString() + "'");
        }
    }

    private static void testAppendReplacementNoMatch() {
        Pattern p = Pattern.compile("abc");
        Matcher m = p.matcher("def");
        // No match attempted, so first < 0 internally
        StringBuffer sb = new StringBuffer();
        try {
            m.appendReplacement(sb, "xyz");
            throw new RuntimeException("Expected IllegalStateException when calling appendReplacement without a match");
        } catch (IllegalStateException ise) {
            // Expected exception
        }
    }
}
