/*
 * @test
 * @summary Witup-derived checks for Matcher.group() behavior without a match and after successful matches
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testGroupAfterSuccessfulFind();
        testGroupAfterSuccessfulMatches();
        testGroupWithoutAnyMatchThrows();
        testGroupAfterFailedFindThrows();
        testGroupAfterFailedMatchesThrows();
    }

    static void testGroupAfterSuccessfulFind() {
        Matcher m = Pattern.compile("b+").matcher("aabbbc");
        check(m.find(), "find should succeed");
        check("bbb".equals(m.group()), "group() should return whole match");
    }

    static void testGroupAfterSuccessfulMatches() {
        Matcher m = Pattern.compile("(a)(b)?").matcher("ab");
        check(m.matches(), "matches should succeed");
        check("ab".equals(m.group()), "group() should return entire matched text");
    }

    static void testGroupWithoutAnyMatchThrows() {
        Matcher m = Pattern.compile("x").matcher("abc");
        expectIllegalState(m, "group() before any match operation");
    }

    static void testGroupAfterFailedFindThrows() {
        Matcher m = Pattern.compile("x").matcher("abc");
        check(!m.find(), "find should fail");
        expectIllegalState(m, "group() after failed find");
    }

    static void testGroupAfterFailedMatchesThrows() {
        Matcher m = Pattern.compile("abc").matcher("abcd");
        check(!m.matches(), "matches should fail for partial input");
        expectIllegalState(m, "group() after failed matches");
    }

    static void expectIllegalState(Matcher m, String context) {
        try {
            m.group();
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException: " + context);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
