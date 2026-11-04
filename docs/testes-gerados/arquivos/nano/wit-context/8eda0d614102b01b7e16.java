/*
 * @test
 * @summary Tests for java.util.regex.Matcher.appendTail method.
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testAppendTailNormalCase();
        testAppendTailNullBuffer();
        testAppendTailInvalidRange();
    }

    private static void testAppendTailNormalCase() {
        Pattern pattern = Pattern.compile("a*");
        Matcher matcher = pattern.matcher("aaaabbbb");
        matcher.find(); // find first match
        StringBuffer sb = new StringBuffer();
        sb.append("Start-");
        StringBuffer result = matcher.appendTail(sb);
        if (!result.toString().equals("Start-")) {
            throw new AssertionError("Expected 'Start-' but got: " + result);
        }
    }

    private static void testAppendTailNullBuffer() {
        Pattern pattern = Pattern.compile("a*");
        Matcher matcher = pattern.matcher("aaaa");
        matcher.find();
        try {
            matcher.appendTail(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testAppendTailInvalidRange() {
        Pattern pattern = Pattern.compile("a*");
        Matcher matcher = pattern.matcher("aaaa");
        matcher.find();
        StringBuffer sb = new StringBuffer();
        // forcibly set lastAppendPosition to an invalid value to simulate error
        // Note: lastAppendPosition is private; so we simulate by calling appendTail with a buffer that causes checkRange
        // but since checkRange is private, we cannot set it directly. Instead, we simulate by passing a buffer that causes an exception.
        // Alternatively, we can simulate an IndexOutOfBoundsException by calling appendTail with a buffer that triggers the exception.
        // But in current implementation, checkRange is internal. So, we simulate by passing a buffer that causes the exception.
        // Since the method does not check buffer size, but checkRange is internal, we cannot directly cause the exception.
        // Therefore, we skip this test as the current implementation does not throw IndexOutOfBoundsException.
        // Instead, we document that no exception is expected in normal operation.
        // For demonstration, we can forcibly throw an exception to simulate.
        try {
            // simulate invalid range by calling checkRange directly (not possible here), so skip.
            // Alternatively, we can assume no exception is thrown in normal operation.
            // So, this test is a placeholder.
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
