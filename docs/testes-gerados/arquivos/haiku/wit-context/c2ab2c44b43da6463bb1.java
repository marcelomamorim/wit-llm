/*
 * @test
 * @summary Verifies Matcher.end() throws IllegalStateException when no match is available and returns the match end otherwise
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testEndBeforeAnyFindThrows();
        testEndAfterSuccessfulFindReturnsMatchEnd();
        testEndAfterFailedFindThrows();
        testEndAfterResetThrows();
        testEndAfterMatchesReturnsInputLength();
    }

    static void testEndBeforeAnyFindThrows() {
        Matcher m = Pattern.compile("b").matcher("abc");
        expectIllegalState(m, "end() before any find");
    }

    static void testEndAfterSuccessfulFindReturnsMatchEnd() {
        Matcher m = Pattern.compile("b").matcher("abc");
        if (!m.find()) {
            throw new RuntimeException("expected find() to succeed");
        }
        int end = m.end();
        if (end != 2) {
            throw new RuntimeException("expected end() == 2 but was " + end);
        }
    }

    static void testEndAfterFailedFindThrows() {
        Matcher m = Pattern.compile("z").matcher("abc");
        if (m.find()) {
            throw new RuntimeException("expected find() to fail");
        }
        expectIllegalState(m, "end() after failed find");
    }

    static void testEndAfterResetThrows() {
        Matcher m = Pattern.compile("b").matcher("abc");
        if (!m.find()) {
            throw new RuntimeException("expected find() to succeed");
        }
        m.reset();
        expectIllegalState(m, "end() after reset()");
    }

    static void testEndAfterMatchesReturnsInputLength() {
        String input = "abc";
        Matcher m = Pattern.compile("abc").matcher(input);
        if (!m.matches()) {
            throw new RuntimeException("expected matches() to succeed");
        }
        int end = m.end();
        if (end != input.length()) {
            throw new RuntimeException("expected end() == " + input.length() + " but was " + end);
        }
    }

    static void expectIllegalState(Matcher m, String label) {
        try {
            int value = m.end();
            throw new RuntimeException(label + " should throw IllegalStateException but returned " + value);
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
