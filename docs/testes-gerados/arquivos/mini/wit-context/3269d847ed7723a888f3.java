/*
 * @test
 * @summary Deterministic tests for java.util.regex.Matcher.group(String) method
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testGroupReturnsNullForUnmatchedGroup();
        testGroupReturnsCorrectSubstringForMatchedGroup();
        testGroupReturnsNullForNonexistentGroupName();
    }

    private static void testGroupReturnsNullForUnmatchedGroup() {
        Pattern p = Pattern.compile("(a)?(b)");
        Matcher m = p.matcher("b");
        if (!m.find()) {
            throw new RuntimeException("Setup failure: expected to find a match");
        }
        // Group 1 is optional and not matched, group 2 is matched
        String result = m.group("1"); // group names are not defined, so this returns null
        // Since no named groups, group("1") returns null because groups[2] or groups[3] == -1
        if (result != null) {
            throw new RuntimeException("Expected null for unmatched group name '1', got: " + result);
        }
    }

    private static void testGroupReturnsCorrectSubstringForMatchedGroup() {
        Pattern p = Pattern.compile("(?<first>a)(?<second>b)");
        Matcher m = p.matcher("ab");
        if (!m.find()) {
            throw new RuntimeException("Setup failure: expected to find a match");
        }
        String first = m.group("first");
        String second = m.group("second");
        if (!"a".equals(first)) {
            throw new RuntimeException("Expected group 'first' to be 'a', got: " + first);
        }
        if (!"b".equals(second)) {
            throw new RuntimeException("Expected group 'second' to be 'b', got: " + second);
        }
    }

    private static void testGroupReturnsNullForNonexistentGroupName() {
        Pattern p = Pattern.compile("(?<x>a)");
        Matcher m = p.matcher("a");
        if (!m.find()) {
            throw new RuntimeException("Setup failure: expected to find a match");
        }
        // The group name "y" does not exist, so group("y") returns null
        String result = m.group("y");
        if (result != null) {
            throw new RuntimeException("Expected null for nonexistent group name 'y', got: " + result);
        }
    }
}
