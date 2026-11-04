/*
 * @test
 * @summary Test java.util.regex.Matcher.group(int) for exception and normal cases
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testGroupNegativeGroupIndex();
        testGroupTooLargeGroupIndex();
        testGroupNoMatchReturnsNull();
        testGroupValidGroupReturnsCorrectSubstring();
    }

    private static void testGroupNegativeGroupIndex() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        if (!m.find()) {
            throw new RuntimeException("Setup failed: pattern did not match input");
        }
        try {
            m.group(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative group index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testGroupTooLargeGroupIndex() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        if (!m.find()) {
            throw new RuntimeException("Setup failed: pattern did not match input");
        }
        int groupCount = m.groupCount();
        try {
            m.group(groupCount + 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for group index > groupCount");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testGroupNoMatchReturnsNull() {
        Pattern p = Pattern.compile("(a)?(b)?");
        Matcher m = p.matcher("a");
        if (!m.find()) {
            throw new RuntimeException("Setup failed: pattern did not match input");
        }
        // group 2 did not participate in the match, should return null
        String group2 = m.group(2);
        if (group2 != null) {
            throw new RuntimeException("Expected null for unmatched optional group");
        }
    }

    private static void testGroupValidGroupReturnsCorrectSubstring() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        if (!m.find()) {
            throw new RuntimeException("Setup failed: pattern did not match input");
        }
        String g0 = m.group(0); // entire match
        String g1 = m.group(1); // first group
        String g2 = m.group(2); // second group
        if (!"ab".equals(g0)) {
            throw new RuntimeException("Expected group(0) to be 'ab', got: " + g0);
        }
        if (!"a".equals(g1)) {
            throw new RuntimeException("Expected group(1) to be 'a', got: " + g1);
        }
        if (!"b".equals(g2)) {
            throw new RuntimeException("Expected group(2) to be 'b', got: " + g2);
        }
    }
}
