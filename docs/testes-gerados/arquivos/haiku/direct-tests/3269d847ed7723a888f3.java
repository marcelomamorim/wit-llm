/*
 * @test
 * @summary Verify Matcher.group(String name) returns the named group's subsequence, null for unmatched groups, and empty string for empty matches.
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testNamedGroupsAfterFind();
        testUnmatchedOptionalGroupReturnsNull();
        testSuccessiveFindUpdatesGroup();
        testEmptyMatchReturnsEmptyString();
    }

    private static void testNamedGroupsAfterFind() {
        Matcher m = Pattern.compile("(?<year>\\d{4})-(?<month>\\d{2})").matcher("2018-09");
        check(m.find(), "find should succeed");
        check("2018".equals(m.group("year")), "year group should be 2018");
        check("09".equals(m.group("month")), "month group should be 09");
    }

    private static void testUnmatchedOptionalGroupReturnsNull() {
        Matcher m = Pattern.compile("(?<a>x)?y").matcher("y");
        check(m.matches(), "matches should succeed");
        check(m.group("a") == null, "unmatched optional group should return null");
    }

    private static void testSuccessiveFindUpdatesGroup() {
        Matcher m = Pattern.compile("(?<d>\\d)").matcher("a1b2");
        check(m.find(), "first find should succeed");
        check("1".equals(m.group("d")), "first digit should be 1");
        check(m.find(), "second find should succeed");
        check("2".equals(m.group("d")), "second digit should be 2");
    }

    private static void testEmptyMatchReturnsEmptyString() {
        Matcher m = Pattern.compile("(?<e>a*)").matcher("b");
        check(m.find(), "find should succeed on empty match");
        check("".equals(m.group("e")), "empty match group should be empty string, not null");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
