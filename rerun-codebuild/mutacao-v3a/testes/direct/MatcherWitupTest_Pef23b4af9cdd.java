/*
 * @test
 * @summary Test for Matcher.start(String name) method behavior.
 * @run main MatcherWitupTest_Pef23b4af9cdd
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_Pef23b4af9cdd {

    public static void main(String[] args) {
        testStartWithNamedGroup();
        testStartWithNonexistentGroup();
        testStartWithNullGroup();
    }

    private static void testStartWithNamedGroup() {
        Pattern p = Pattern.compile("(?<g1>abc)(?<g2>def)");
        Matcher m = p.matcher("abcdef");
        boolean found = m.find();
        if (!found) {
            throw new AssertionError("Expected to find a match");
        }
        int startG1 = m.start("g1");
        int startG2 = m.start("g2");
        if (startG1 != 0 || startG2 != 3) {
            throw new AssertionError("Unexpected start positions: g1=" + startG1 + ", g2=" + startG2);
        }
    }

    private static void testStartWithNonexistentGroup() {
        Pattern p = Pattern.compile("(abc)");
        Matcher m = p.matcher("abc");
        boolean found = m.find();
        if (!found) {
            throw new AssertionError("Expected to find a match");
        }
        try {
            m.start("nonexistent");
            throw new AssertionError("Expected IllegalArgumentException for nonexistent group");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testStartWithNullGroup() {
        Pattern p = Pattern.compile("(abc)");
        Matcher m = p.matcher("abc");
        boolean found = m.find();
        if (!found) {
            throw new AssertionError("Expected to find a match");
        }
        try {
            m.start(null);
            throw new AssertionError("Expected NullPointerException for null group");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
