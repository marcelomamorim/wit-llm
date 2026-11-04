/*
 * @test
 * @summary Verifies Matcher.start(String) returns the start index of named groups after a match
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testNamedGroupStartIndices();
        testNamedGroupMatchesNumberedGroup();
        testUnmatchedOptionalGroupReturnsMinusOne();
        testStartUpdatesAfterSubsequentFind();
    }

    static void testNamedGroupStartIndices() {
        Pattern p = Pattern.compile("(?<year>\\d{4})-(?<month>\\d{2})");
        Matcher m = p.matcher("date: 2018-09 end");
        check(m.find(), "find should succeed");
        check(m.start("year") == 6, "year start expected 6 but was " + m.start("year"));
        check(m.start("month") == 11, "month start expected 11 but was " + m.start("month"));
    }

    static void testNamedGroupMatchesNumberedGroup() {
        Pattern p = Pattern.compile("(?<year>\\d{4})-(?<month>\\d{2})");
        Matcher m = p.matcher("date: 2018-09 end");
        check(m.find(), "find should succeed");
        check(m.start("year") == m.start(1), "named group 'year' should equal group 1 start");
    }

    static void testUnmatchedOptionalGroupReturnsMinusOne() {
        Pattern p = Pattern.compile("(?<a>x)?y");
        Matcher m = p.matcher("y");
        check(m.find(), "find should succeed");
        check(m.start("a") == -1, "unmatched optional group start expected -1 but was " + m.start("a"));
    }

    static void testStartUpdatesAfterSubsequentFind() {
        Pattern p = Pattern.compile("(?<w>a)");
        Matcher m = p.matcher("aba");
        check(m.find(), "first find should succeed");
        check(m.start("w") == 0, "first start expected 0 but was " + m.start("w"));
        check(m.find(), "second find should succeed");
        check(m.start("w") == 2, "second start expected 2 but was " + m.start("w"));
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
