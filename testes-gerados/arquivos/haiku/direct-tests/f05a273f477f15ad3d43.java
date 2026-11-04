/*
 * @test
 * @summary Verify Matcher.group(int) state checks, index bounds, capture results and unmatched groups
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testGroupBeforeMatchThrows();
        testGroupZeroAndCaptures();
        testMatchesCapture();
        testOutOfBoundsIndexes();
        testUnmatchedGroupReturnsNull();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testGroupBeforeMatchThrows() {
        Matcher m = Pattern.compile("a").matcher("bbb");
        boolean thrown = false;
        try {
            m.group(0);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "group(0) before any match must throw IllegalStateException");
    }

    static void testGroupZeroAndCaptures() {
        Matcher m = Pattern.compile("(a)(b)").matcher("xaby");
        check(m.find(), "find should succeed on xaby");
        check("ab".equals(m.group(0)), "group(0) should be ab");
        check("a".equals(m.group(1)), "group(1) should be a");
        check("b".equals(m.group(2)), "group(2) should be b");
    }

    static void testMatchesCapture() {
        Matcher m = Pattern.compile("(x+)").matcher("xxx");
        check(m.matches(), "matches should succeed on xxx");
        check("xxx".equals(m.group(1)), "group(1) should be xxx");
    }

    static void testOutOfBoundsIndexes() {
        Matcher m = Pattern.compile("(a)").matcher("a");
        check(m.matches(), "matches should succeed on a");
        check(m.groupCount() == 1, "groupCount should be 1");

        boolean negative = false;
        try {
            m.group(-1);
        } catch (IndexOutOfBoundsException e) {
            negative = true;
        }
        check(negative, "group(-1) must throw IndexOutOfBoundsException");

        boolean tooLarge = false;
        try {
            m.group(2);
        } catch (IndexOutOfBoundsException e) {
            tooLarge = true;
        }
        check(tooLarge, "group(groupCount()+1) must throw IndexOutOfBoundsException");
    }

    static void testUnmatchedGroupReturnsNull() {
        Matcher m = Pattern.compile("(a)|(b)").matcher("a");
        check(m.find(), "find should succeed on a");
        check("a".equals(m.group(1)), "group(1) should be a");
        check(m.group(2) == null, "unmatched group(2) should be null");
    }
}
