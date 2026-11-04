/*
 * @test
 * @summary Test java.util.regex.Matcher.group(int) method for expected exceptions and normal behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testGroupNoMatchFound();
        testGroupIndexOutOfBounds();
        testGroupReturnsNullForUnmatchedGroup();
        testGroupReturnsCorrectSubstring();
    }

    private static void testGroupNoMatchFound() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher(""); // no match
        try {
            m.group(0);
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    private static void testGroupIndexOutOfBounds() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        m.find();
        try {
            m.group(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative group not thrown");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            m.group(m.groupCount() + 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for group > groupCount not thrown");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testGroupReturnsNullForUnmatchedGroup() {
        Pattern p = Pattern.compile("(a)?(b)");
        Matcher m = p.matcher("b");
        m.find();
        // group 1 is optional and unmatched, should return null
        String g1 = m.group(1);
        if (g1 != null) {
            throw new RuntimeException("Expected null for unmatched optional group, got: " + g1);
        }
        // group 2 matched
        String g2 = m.group(2);
        if (!"b".equals(g2)) {
            throw new RuntimeException("Expected 'b' for group 2, got: " + g2);
        }
    }

    private static void testGroupReturnsCorrectSubstring() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Expected to find a match");
        }
        String g0 = m.group(0);
        if (!"ab".equals(g0)) {
            throw new RuntimeException("Expected group 0 to be 'ab', got: " + g0);
        }
        String g1 = m.group(1);
        if (!"a".equals(g1)) {
            throw new RuntimeException("Expected group 1 to be 'a', got: " + g1);
        }
        String g2 = m.group(2);
        if (!"b".equals(g2)) {
            throw new RuntimeException("Expected group 2 to be 'b', got: " + g2);
        }
    }
}
