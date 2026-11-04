/*
 * @test
 * @summary Verifies Matcher.start() returns the start of the last successful match and throws IllegalStateException when no match is available
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testStartAfterFindReturnsMatchStart();
        testStartBeforeAnyMatchThrows();
        testStartAfterFailedFindThrows();
        testStartAfterSuccessfulMatchesReturnsZero();
        testStartAfterFailedMatchesThrows();
        testStartAfterResetThrows();
        testStartAfterSecondFindReturnsNewStart();
    }

    static void testStartAfterFindReturnsMatchStart() {
        Matcher m = Pattern.compile("b").matcher("aab");
        if (!m.find()) {
            throw new RuntimeException("find() should succeed");
        }
        if (m.start() != 2) {
            throw new RuntimeException("Expected start 2 but got " + m.start());
        }
    }

    static void testStartBeforeAnyMatchThrows() {
        Matcher m = Pattern.compile("b").matcher("aab");
        expectIllegalState(m);
    }

    static void testStartAfterFailedFindThrows() {
        Matcher m = Pattern.compile("x").matcher("abc");
        if (m.find()) {
            throw new RuntimeException("find() should fail");
        }
        expectIllegalState(m);
    }

    static void testStartAfterSuccessfulMatchesReturnsZero() {
        Matcher m = Pattern.compile("abc").matcher("abc");
        if (!m.matches()) {
            throw new RuntimeException("matches() should succeed");
        }
        if (m.start() != 0) {
            throw new RuntimeException("Expected start 0 but got " + m.start());
        }
    }

    static void testStartAfterFailedMatchesThrows() {
        Matcher m = Pattern.compile("abc").matcher("abcd");
        if (m.matches()) {
            throw new RuntimeException("matches() should fail");
        }
        expectIllegalState(m);
    }

    static void testStartAfterResetThrows() {
        Matcher m = Pattern.compile("a").matcher("a");
        if (!m.find()) {
            throw new RuntimeException("find() should succeed");
        }
        m.reset();
        expectIllegalState(m);
    }

    static void testStartAfterSecondFindReturnsNewStart() {
        Matcher m = Pattern.compile("a").matcher("xaya");
        if (!m.find() || m.start() != 1) {
            throw new RuntimeException("First find start should be 1");
        }
        if (!m.find() || m.start() != 3) {
            throw new RuntimeException("Second find start should be 3");
        }
    }

    static void expectIllegalState(Matcher m) {
        try {
            m.start();
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException from start()");
    }
}
