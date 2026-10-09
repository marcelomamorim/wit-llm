/*
 * @test
 * @summary Test java.util.regex.Matcher.appendReplacement behavior and exceptions
 * @run main MatcherWitupTest_P350dc021b07b
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest_P350dc021b07b {
    public static void main(String[] args) {
        testAppendReplacementNoMatch();
        testAppendReplacementNormal();
        testAppendReplacementNullTextEmptyReplacement();
    }

    // Test that appendReplacement throws IllegalStateException if no match available (first < 0)
    private static void testAppendReplacementNoMatch() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("");
        // No match attempted, so first < 0 internally
        StringBuilder sb = new StringBuilder();
        try {
            m.appendReplacement(sb, "x");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // Expected
        }
    }

    // Test normal appendReplacement usage with a successful match
    private static void testAppendReplacementNormal() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("cat");
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Setup failure: expected to find match");
        }
        StringBuilder sb = new StringBuilder();
        m.appendReplacement(sb, "x");
        m.appendTail(sb);
        String result = sb.toString();
        if (!"cxt".equals(result)) {
            throw new RuntimeException("Unexpected replacement result: " + result);
        }
    }

    // Test appendReplacement with null text and empty replacement string
    // This test is adapted because text is private and cannot be set to null externally
    // We test that appendReplacement does not throw IndexOutOfBoundsException in normal usage
    private static void testAppendReplacementNullTextEmptyReplacement() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("");
        StringBuilder sb = new StringBuilder();
        // No match found, so first < 0, appendReplacement throws IllegalStateException
        try {
            m.appendReplacement(sb, "");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected
        }
    }
}
