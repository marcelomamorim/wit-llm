/*
 * @test
 * @summary Verifies Matcher.end(String name) returns the end index of named groups and -1 for unmatched optional groups
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testNamedGroupEnd();
        testEndByNameMatchesEndByIndex();
        testUnmatchedOptionalGroupEndIsMinusOne();
    }

    static void testNamedGroupEnd() {
        Matcher m = Pattern.compile("(?<year>\\d{4})-(?<month>\\d{2})")
                           .matcher("date 2024-05");
        check(m.find(), "find should succeed");
        check(m.end("year") == 9, "end(year) expected 9 but was " + m.end("year"));
        check(m.end("month") == 12, "end(month) expected 12 but was " + m.end("month"));
    }

    static void testEndByNameMatchesEndByIndex() {
        Matcher m = Pattern.compile("(?<year>\\d{4})-(?<month>\\d{2})")
                           .matcher("2024-05");
        check(m.matches(), "matches should succeed");
        check(m.end("year") == m.end(1), "end(year) should equal end(1)");
        check(m.end("month") == m.end(2), "end(month) should equal end(2)");
        check(m.end("month") == 7, "end(month) expected 7 but was " + m.end("month"));
    }

    static void testUnmatchedOptionalGroupEndIsMinusOne() {
        Matcher m = Pattern.compile("(?<a>x)?y").matcher("y");
        check(m.find(), "find should succeed");
        check(m.end("a") == -1, "end(a) expected -1 but was " + m.end("a"));
        check(m.end() == 1, "end() expected 1 but was " + m.end());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
