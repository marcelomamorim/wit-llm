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
        testGroupInvalidNegative();
        testGroupInvalidTooLarge();
        testGroupNullWhenNotMatched();
    }

    private static void testGroupValid() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        if (!m.matches()) {
            throw new RuntimeException("Setup failed: pattern did not match input");
        }
        // group 0 is entire match
        String g0 = m.group(0);
        if (!"ab".equals(g0)) {
            throw new RuntimeException("Expected group(0) to be 'ab' but was '" + g0 + "'");
        }
        // group 1 is 'a'
        String g1 = m.group(1);
        if (!"a".equals(g1)) {
            throw new RuntimeException("Expected group(1) to be 'a' but was '" + g1 + "'");
        }
        // group 2 is 'b'
        String g2 = m.group(2);
        if (!"b".equals(g2)) {
            throw new RuntimeException("Expected group(2) to be 'b' but was '" + g2 + "'");
        }
    }

    private static void testGroupInvalidNegative() {
        Pattern p = Pattern.compile("(a)");
        Matcher m = p.matcher("a");
        if (!m.matches()) {
            throw new RuntimeException("Setup failed: pattern did not match input");
        }
        try {
            m.group(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for group(-1)");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testGroupInvalidTooLarge() {
        Pattern p = Pattern.compile("(a)");
        Matcher m = p.matcher("a");
        if (!m.matches()) {
            throw new RuntimeException("Setup failed: pattern did not match input");
        }
        int groupCount = m.groupCount();
        try {
            m.group(groupCount + 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for group(" + (groupCount + 1) + ")");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testGroupNullWhenNotMatched() {
        Pattern p = Pattern.compile("(a)?b");
        Matcher m = p.matcher("b");
        if (!m.matches()) {
            throw new RuntimeException("Setup failed: pattern did not match input");
        }
        // group 1 is optional and not matched, should return null
        String g1 = m.group(1);
        if (g1 != null) {
            throw new RuntimeException("Expected group(1) to be null when not matched but was '" + g1 + "'");
        }
    }
}