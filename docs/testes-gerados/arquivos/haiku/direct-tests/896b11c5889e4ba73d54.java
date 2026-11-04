/*
 * @test
 * @summary Verifies Matcher.group() returns the whole matched subsequence after find, matches and lookingAt
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        Matcher m = Pattern.compile("a+b").matcher("xxaaab");
        check(m.find(), "find should succeed");
        String g = m.group();
        check("aaab".equals(g), "group() after find expected aaab but was " + g);

        m = Pattern.compile("hello").matcher("hello");
        check(m.matches(), "matches should succeed");
        g = m.group();
        check("hello".equals(g), "group() after matches expected hello but was " + g);

        m = Pattern.compile("(a)(b)").matcher("zabz");
        check(m.find(), "find should succeed for groups");
        g = m.group();
        check("ab".equals(g), "group() must return whole match ab but was " + g);
        check("a".equals(m.group(1)), "group(1) expected a");

        m = Pattern.compile("[0-9]+").matcher("a1b22");
        check(m.find(), "first find should succeed");
        g = m.group();
        check("1".equals(g), "first group() expected 1 but was " + g);
        check(m.find(), "second find should succeed");
        g = m.group();
        check("22".equals(g), "second group() expected 22 but was " + g);
        check(!m.find(), "third find should fail");

        m = Pattern.compile("x*").matcher("");
        check(m.find(), "find on empty input should match empty string");
        g = m.group();
        check("".equals(g), "group() expected empty string but was " + g);

        m = Pattern.compile("ab").matcher("abc");
        check(m.lookingAt(), "lookingAt should succeed");
        g = m.group();
        check("ab".equals(g), "group() after lookingAt expected ab but was " + g);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
