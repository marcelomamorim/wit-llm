/* @test    @summary Test for Matcher.start(String name) method in java.util.regex.Matcher    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testStartWithNamedGroup();
        testStartWithNonexistentGroup();
    }

    private static void testStartWithNamedGroup() {
        Pattern pattern = Pattern.compile("(?<foo>abc)(?<bar>def)");
        Matcher matcher = pattern.matcher("abcdef");
        if (matcher.find()) {
            int startFoo = matcher.start("foo");
            int startBar = matcher.start("bar");
            assert startFoo == 0 : "Expected start of 'foo' group to be 0";
            assert startBar == 3 : "Expected start of 'bar' group to be 3";
        } else {
            throw new AssertionError("Pattern did not match input");
        }
    }

    private static void testStartWithNonexistentGroup() {
        Pattern pattern = Pattern.compile("(abc)");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.find()) {
            try {
                matcher.start("nonexistent");
                throw new AssertionError("Expected IllegalArgumentException for nonexistent group");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }
        } else {
            throw new AssertionError("Pattern did not match input");
        }
    }
}
