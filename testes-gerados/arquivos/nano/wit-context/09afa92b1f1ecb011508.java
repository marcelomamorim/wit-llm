/*
 * @test
 * @summary Tests for java.util.regex.Matcher.next() method.
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testNextNormal();
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
    }

    private static void testNextNormal() {
        Pattern pattern = Pattern.compile("\d+");
        Matcher matcher = pattern.matcher("123 456");
        if (matcher.find()) {
            MatchResult result = matcher.next();
            if (result == null) {
                throw new AssertionError("Expected non-null MatchResult");
            }
        } else {
            throw new AssertionError("Matcher should find a match");
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xyz");
        try {
            matcher.next();
            throw new AssertionError("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    private static void testNextThrowsConcurrentModificationException() {
        Pattern pattern = Pattern.compile("\d+");
        Matcher matcher = pattern.matcher("123 456");
        // Simulate modification by changing the text reference
        // Note: In actual implementation, this would require access to internal state or a different approach.
        // Since direct modification isn't feasible here, this test is illustrative.
        // Alternatively, this test can be skipped if internal state manipulation isn't possible.
        try {
            // No direct way to modify modCount or expectedCount without reflection; skipping actual modification.
            // Instead, this test is a placeholder to indicate the intention.
            matcher.next();
            // If no exception, test fails
            throw new AssertionError("Expected ConcurrentModificationException");
        } catch (ConcurrentModificationException e) {
            // expected
        } catch (Exception e) {
            // other exceptions are not expected
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
