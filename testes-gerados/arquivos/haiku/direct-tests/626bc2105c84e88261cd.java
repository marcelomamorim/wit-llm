/*
 * @test
 * @summary Behavioral checks for Matcher.end() on match results, empty matches, groups, regions and before/after failed matches
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testSuccessiveFindEndIndices();
        testEmptyMatchEnd();
        testEndReflectsWholeMatchNotGroup();
        testEndWithinRegion();
        testEndAfterFailedMatchThrows();
        testEndBeforeAnyMatchThrows();
    }

    static void testSuccessiveFindEndIndices() {
        Matcher m = Pattern.compile("ab").matcher("xxabyyab");
        check(m.find(), "first find should succeed");
        check(m.end() == 4, "end after first match should be 4, was " + m.end());
        check(m.find(), "second find should succeed");
        check(m.end() == 8, "end after second match should be 8, was " + m.end());
        check(!m.find(), "third find should fail");
    }

    static void testEmptyMatchEnd() {
        Matcher m = Pattern.compile("x*").matcher("ab");
        check(m.find(), "empty-matching pattern should find at start");
        check(m.end() == 0, "end of empty match should be 0, was " + m.end());
    }

    static void testEndReflectsWholeMatchNotGroup() {
        Matcher m = Pattern.compile("(a)(b)").matcher("zab");
        check(m.find(), "group pattern should match");
        check(m.start() == 1, "start should be 1");
        check(m.end() == 3, "end should be 3 for whole match, was " + m.end());
    }

    static void testEndWithinRegion() {
        Matcher m = Pattern.compile("a").matcher("aaa");
        m.region(1, 3);
        check(m.find(), "find within region should succeed");
        check(m.end() == 2, "end within region should be 2, was " + m.end());
    }

    static void testEndAfterFailedMatchThrows() {
        Matcher m = Pattern.compile("a").matcher("xyz");
        check(!m.matches(), "matches() should fail on xyz");
        try {
            m.end();
            fail("end() after failed match should throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testEndBeforeAnyMatchThrows() {
        Matcher m = Pattern.compile("a").matcher("a");
        try {
            m.end();
            fail("end() before any match attempt should throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void fail(String message) {
        throw new RuntimeException(message);
    }
}
