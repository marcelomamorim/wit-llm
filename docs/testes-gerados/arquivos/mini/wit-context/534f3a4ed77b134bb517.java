/*
 * @test
 * @summary Test java.util.regex.Matcher.group() method for expected exceptions and normal behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testGroupWithoutMatch();
        testGroupWithMatch();
    }

    private static void testGroupWithoutMatch() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("b");
        // No match attempted, so group() should throw IllegalStateException
        try {
            m.group();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // Expected
        }
    }

    private static void testGroupWithMatch() {
        Pattern p = Pattern.compile("(a)");
        Matcher m = p.matcher("a");
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Setup failure: expected to find a match");
        }
        // group() should return the entire match
        String g0 = m.group();
        if (!"a".equals(g0)) {
            throw new RuntimeException("group() returned unexpected value: " + g0);
        }
        // group(0) should be same as group()
        String g0index = m.group(0);
        if (!g0.equals(g0index)) {
            throw new RuntimeException("group() and group(0) differ");
        }
        // group(1) should return the first capturing group
        String g1 = m.group(1);
        if (!"a".equals(g1)) {
            throw new RuntimeException("group(1) returned unexpected value: " + g1);
        }
    }
}
