/*
 * @test
 * @summary Verifies Matcher.end() returns the end index after a successful match and throws IllegalStateException when no match is available
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testEndAfterSuccessfulFind();
        testEndAfterMatches();
        testEndBeforeAnyMatchThrows();
        testEndAfterFailedFindThrows();
        testEndAfterResetThrows();
    }

    static void testEndAfterSuccessfulFind() {
        Matcher m = Pattern.compile("a").matcher("ba");
        if (!m.find()) {
            throw new RuntimeException("expected find() to succeed");
        }
        if (m.end() != 2) {
            throw new RuntimeException("expected end() == 2, was " + m.end());
        }
    }

    static void testEndAfterMatches() {
        Matcher m = Pattern.compile("ab").matcher("ab");
        if (!m.matches()) {
            throw new RuntimeException("expected matches() to succeed");
        }
        if (m.end() != 2) {
            throw new RuntimeException("expected end() == 2, was " + m.end());
        }
    }

    static void testEndBeforeAnyMatchThrows() {
        Matcher m = Pattern.compile("a").matcher("b");
        expectIllegalState(m);
    }

    static void testEndAfterFailedFindThrows() {
        Matcher m = Pattern.compile("a").matcher("b");
        if (m.find()) {
            throw new RuntimeException("expected find() to fail");
        }
        expectIllegalState(m);
    }

    static void testEndAfterResetThrows() {
        Matcher m = Pattern.compile("a").matcher("a");
        if (!m.find()) {
            throw new RuntimeException("expected find() to succeed");
        }
        m.reset();
        expectIllegalState(m);
    }

    static void expectIllegalState(Matcher m) {
        try {
            m.end();
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("expected IllegalStateException from end()");
    }
}
