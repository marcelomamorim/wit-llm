/*
 * @test
 * @summary Test java.util.regex.Matcher.appendTail(StringBuilder) for correct append and boundary conditions
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testAppendTailNormal();
        testAppendTailWithEmptyRegion();
        testAppendTailWithLastAppendPositionAtTextLength();
    }

    private static void testAppendTailNormal() {
        String input = "abc123xyz";
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(input);
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Setup failed: expected to find digits in input");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(input.substring(0, m.start())); // append prefix before match
        StringBuilder result = m.appendTail(sb);
        String expected = input.substring(0, m.start()) + input.substring(m.end());
        if (!result.toString().equals(expected)) {
            throw new RuntimeException("appendTail did not append the expected tail. Expected: '" + expected + "' but got: '" + result.toString() + "'");
        }
    }

    private static void testAppendTailWithEmptyRegion() {
        String input = "hello";
        Pattern p = Pattern.compile("world"); // no match
        Matcher m = p.matcher(input);
        boolean found = m.find();
        if (found) {
            throw new RuntimeException("Setup failed: expected no match");
        }
        // lastAppendPosition is 0 by default (no match found)
        StringBuilder sb = new StringBuilder();
        sb.append("prefix-");
        StringBuilder result = m.appendTail(sb);
        String expected = "prefix-" + input;
        if (!result.toString().equals(expected)) {
            throw new RuntimeException("appendTail with no match did not append entire input. Expected: '" + expected + "' but got: '" + result.toString() + "'");
        }
    }

    private static void testAppendTailWithLastAppendPositionAtTextLength() {
        String input = "test";
        Pattern p = Pattern.compile("test");
        Matcher m = p.matcher(input);
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Setup failed: expected to find 'test'");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(input.substring(0, m.start()));
        // call appendTail after appendReplacement to set lastAppendPosition to end
        m.appendReplacement(sb, "repl");
        StringBuilder result = m.appendTail(sb);
        // Since lastAppendPosition == input.length(), appendTail should append empty string
        String expected = input.substring(0, m.start()) + "repl";
        if (!result.toString().equals(expected)) {
            throw new RuntimeException("appendTail after appendReplacement did not behave as expected. Expected: '" + expected + "' but got: '" + result.toString() + "'");
        }
    }
}
