/*
 * @test
 * @summary Checks observable behavior of Matcher.group() for whole-match access and the IllegalStateException when no match is available
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testWholeMatchAfterFind();
        testWholeMatchAfterMatches();
        testGroupZeroIgnoresCapturingGroups();
        testNoMatchAttemptThrows();
        testAfterFailedFindThrows();
        testAfterFailedMatchesThrows();
    }

    static void testWholeMatchAfterFind() {
        Matcher m = Pattern.compile("a+").matcher("xaay");
        if (!m.find()) {
            throw new AssertionError("find() should succeed");
        }
        check("aa", m.group(), "group() after find()");
    }

    static void testWholeMatchAfterMatches() {
        Matcher m = Pattern.compile("[a-c]+").matcher("abc");
        if (!m.matches()) {
            throw new AssertionError("matches() should succeed");
        }
        check("abc", m.group(), "group() after matches()");
    }

    static void testGroupZeroIgnoresCapturingGroups() {
        Matcher m = Pattern.compile("(x)(y)").matcher("zxyz");
        if (!m.find()) {
            throw new AssertionError("find() should succeed");
        }
        check("xy", m.group(), "group() with capturing groups");
        check("x", m.group(1), "group(1) sanity check");
    }

    static void testNoMatchAttemptThrows() {
        Matcher m = Pattern.compile("a").matcher("a");
        expectIllegalState(m);
    }

    static void testAfterFailedFindThrows() {
        Matcher m = Pattern.compile("z").matcher("abc");
        if (m.find()) {
            throw new AssertionError("find() should fail");
        }
        expectIllegalState(m);
    }

    static void testAfterFailedMatchesThrows() {
        Matcher m = Pattern.compile("a").matcher("ab");
        if (m.matches()) {
            throw new AssertionError("matches() should fail");
        }
        expectIllegalState(m);
    }

    static void check(String expected, String actual, String what) {
        if (!expected.equals(actual)) {
            throw new AssertionError(what + ": expected " + expected + " but was " + actual);
        }
    }

    static void expectIllegalState(Matcher m) {
        try {
            String s = m.group();
            throw new AssertionError("expected IllegalStateException but group() returned " + s);
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
