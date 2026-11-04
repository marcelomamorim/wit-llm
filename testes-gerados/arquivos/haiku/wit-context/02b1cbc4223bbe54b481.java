/*
 * @test
 * @summary Verify Matcher.end(int) and MatchResult.end(int) return values and exceptions
 * @run main MatcherWitupTest
 */

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testEndBeforeAnyMatchAttempt();
        testEndAfterFailedMatch();
        testEndAfterSuccessfulFind();
        testEndOptionalGroupNotParticipating();
        testGroupOutOfRange();
        testMatchResultEndBeforeMatch();
        testMatchResultEndAfterFind();
        testMatchResultGroupOutOfRange();
    }

    static void testEndBeforeAnyMatchAttempt() {
        Matcher m = Pattern.compile("a").matcher("a");
        try {
            m.end(0);
            throw new AssertionError("expected IllegalStateException");
        } catch (IllegalStateException expected) {
        }
    }

    static void testEndAfterFailedMatch() {
        Matcher m = Pattern.compile("x").matcher("y");
        if (m.matches()) {
            throw new AssertionError("matches() should be false");
        }
        try {
            m.end(0);
            throw new AssertionError("expected IllegalStateException");
        } catch (IllegalStateException expected) {
        }
    }

    static void testEndAfterSuccessfulFind() {
        Matcher m = Pattern.compile("b+").matcher("aabbbc");
        if (!m.find()) {
            throw new AssertionError("find() should succeed");
        }
        if (m.end(0) != 5) {
            throw new AssertionError("end(0) expected 5 but was " + m.end(0));
        }
    }

    static void testEndOptionalGroupNotParticipating() {
        Matcher m = Pattern.compile("(a)(b)?").matcher("a");
        if (!m.find()) {
            throw new AssertionError("find() should succeed");
        }
        if (m.end(1) != 1) {
            throw new AssertionError("end(1) expected 1 but was " + m.end(1));
        }
        if (m.end(2) != -1) {
            throw new AssertionError("end(2) expected -1 but was " + m.end(2));
        }
    }

    static void testGroupOutOfRange() {
        Matcher m = Pattern.compile("(a)").matcher("a");
        if (!m.find()) {
            throw new AssertionError("find() should succeed");
        }
        try {
            m.end(5);
            throw new AssertionError("expected IndexOutOfBoundsException for 5");
        } catch (IndexOutOfBoundsException expected) {
        }
        try {
            m.end(-1);
            throw new AssertionError("expected IndexOutOfBoundsException for -1");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    static void testMatchResultEndBeforeMatch() {
        Matcher m = Pattern.compile("a").matcher("a");
        MatchResult r = m.toMatchResult();
        try {
            r.end(0);
            throw new AssertionError("expected IllegalStateException");
        } catch (IllegalStateException expected) {
        }
    }

    static void testMatchResultEndAfterFind() {
        Matcher m = Pattern.compile("(b+)").matcher("aabbbc");
        if (!m.find()) {
            throw new AssertionError("find() should succeed");
        }
        MatchResult r = m.toMatchResult();
        if (r.end(0) != 5) {
            throw new AssertionError("end(0) expected 5 but was " + r.end(0));
        }
        if (r.end(1) != 5) {
            throw new AssertionError("end(1) expected 5 but was " + r.end(1));
        }
    }

    static void testMatchResultGroupOutOfRange() {
        Matcher m = Pattern.compile("(a)").matcher("a");
        if (!m.find()) {
            throw new AssertionError("find() should succeed");
        }
        MatchResult r = m.toMatchResult();
        try {
            r.end(2);
            throw new AssertionError("expected IndexOutOfBoundsException for 2");
        } catch (IndexOutOfBoundsException expected) {
        }
        try {
            r.end(-1);
            throw new AssertionError("expected IndexOutOfBoundsException for -1");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
