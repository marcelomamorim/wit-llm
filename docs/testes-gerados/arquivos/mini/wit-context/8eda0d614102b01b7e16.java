/*
 * @test
 * @summary Test java.util.regex.Matcher.appendTail(StringBuffer) for correct behavior and boundary conditions
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testAppendTailNormal();
        testAppendTailEmptyBuffer();
        testAppendTailPartialMatch();
    }

    private static void testAppendTailNormal() {
        String input = "abc123def";
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(input);
        StringBuffer sb = new StringBuffer();

        // Find the digits
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Expected to find digits in input");
        }

        // Append the part before the match
        sb.append(input.substring(0, m.start()));

        // Append the matched digits
        sb.append(m.group());

        // Use appendTail to append the rest
        StringBuffer result = m.appendTail(sb);

        // The result should be the original input
        if (!result.toString().equals(input)) {
            throw new RuntimeException("appendTail did not append the tail correctly: " + result);
        }
    }

    private static void testAppendTailEmptyBuffer() {
        String input = "hello world";
        Pattern p = Pattern.compile("world");
        Matcher m = p.matcher(input);
        StringBuffer sb = new StringBuffer();

        // Find "world"
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Expected to find 'world' in input");
        }

        // Append the part before the match
        sb.append(input.substring(0, m.start()));

        // Append the matched substring
        sb.append(m.group());

        // Append the tail
        StringBuffer result = m.appendTail(sb);

        // The result should be the original input
        if (!result.toString().equals(input)) {
            throw new RuntimeException("appendTail failed on empty initial buffer: " + result);
        }
    }

    private static void testAppendTailPartialMatch() {
        String input = "start middle end";
        Pattern p = Pattern.compile("middle");
        Matcher m = p.matcher(input);
        StringBuffer sb = new StringBuffer();

        // Find "middle"
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Expected to find 'middle' in input");
        }

        // Append the part before the match
        sb.append(input.substring(0, m.start()));

        // Append the matched substring
        sb.append(m.group());

        // Append the tail
        StringBuffer result = m.appendTail(sb);

        // The result should be the original input
        if (!result.toString().equals(input)) {
            throw new RuntimeException("appendTail failed on partial match: " + result);
        }
    }
}
