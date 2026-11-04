/* @test    @summary Test for Matcher.group() method to ensure it returns the matched substring or throws if no match.    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testGroupReturnsMatch();
        testGroupThrowsWhenNoMatch();
    }

    static void testGroupReturnsMatch() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foo bar");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match");
        }
        String result = matcher.group();
        if (!"foo".equals(result)) {
            throw new AssertionError("Expected 'foo', but got: " + result);
        }
    }

    static void testGroupThrowsWhenNoMatch() {
        Pattern pattern = Pattern.compile("baz");
        Matcher matcher = pattern.matcher("foo bar");
        boolean found = matcher.find();
        if (found) {
            throw new AssertionError("Did not expect to find a match");
        }
        try {
            matcher.group();
            throw new AssertionError("Expected IllegalStateException when no match");
        } catch (IllegalStateException e) {
            // expected
        }
    }
}
