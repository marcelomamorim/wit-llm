/*
 * @test
 * @summary Test java.util.regex.Matcher.group() method for expected exceptions and normal behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testGroupOnUnmatchedMatcher();
        testGroupOnMatchedMatcher();
    }

    private static void testGroupOnUnmatchedMatcher() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("b");
        // No match attempted yet, group() should throw IllegalStateException
        try {
            m.group();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    private static void testGroupOnMatchedMatcher() {
        Pattern p = Pattern.compile("(a)(b)?");
        Matcher m = p.matcher("a");
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Setup failure: pattern did not match input");
        }
        // group() with no argument returns group 0 (entire match)
        String group0 = m.group();
        if (!"a".equals(group0)) {
            throw new RuntimeException("group() returned unexpected result: " + group0);
        }
        // group(1) should return "a"
        String group1 = m.group(1);
        if (!"a".equals(group1)) {
            throw new RuntimeException("group(1) returned unexpected result: " + group1);
        }
        // group(2) should return null because group 2 is optional and not matched
        String group2 = m.group(2);
        if (group2 != null) {
            throw new RuntimeException("group(2) expected to be null but was: " + group2);
        }
    }
}
