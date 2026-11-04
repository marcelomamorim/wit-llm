/*
 * @test
 * @summary Test java.util.regex.Matcher.group(int) method behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testGroupValid();
        testGroupNoMatch();
        testGroupIndexOutOfBounds();
        testGroupNullGroup();
    }

    static void testGroupValid() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        if (!m.matches()) {
            throw new RuntimeException("Expected match");
        }
        // group 0 is entire match
        String g0 = m.group(0);
        if (!"ab".equals(g0)) {
            throw new RuntimeException("group(0) expected 'ab' but got '" + g0 + "'");
        }
        // group 1 is 'a'
        String g1 = m.group(1);
        if (!"a".equals(g1)) {
            throw new RuntimeException("group(1) expected 'a' but got '" + g1 + "'");
        }
        // group 2 is 'b'
        String g2 = m.group(2);
        if (!"b".equals(g2)) {
            throw new RuntimeException("group(2) expected 'b' but got '" + g2 + "'");
        }
    }

    static void testGroupNoMatch() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("b");
        try {
            m.group(0);
            throw new RuntimeException("Expected IllegalStateException for no match");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    static void testGroupIndexOutOfBounds() {
        Pattern p = Pattern.compile("(a)");
        Matcher m = p.matcher("a");
        if (!m.matches()) {
            throw new RuntimeException("Expected match");
        }
        try {
            m.group(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative group");
        } catch (IndexOutOfBoundsException iobe) {
            // expected
        }
        try {
            m.group(2); // only group 0 and 1 exist
            throw new RuntimeException("Expected IndexOutOfBoundsException for group > groupCount");
        } catch (IndexOutOfBoundsException iobe) {
            // expected
        }
    }

    static void testGroupNullGroup() {
        // Pattern with optional group that does not participate in the match
        Pattern p = Pattern.compile("a(b)?");
        Matcher m = p.matcher("a");
        if (!m.matches()) {
            throw new RuntimeException("Expected match");
        }
        // group 1 did not match, should return null
        String g1 = m.group(1);
        if (g1 != null) {
            throw new RuntimeException("Expected null for unmatched optional group but got '" + g1 + "'");
        }
    }
}
