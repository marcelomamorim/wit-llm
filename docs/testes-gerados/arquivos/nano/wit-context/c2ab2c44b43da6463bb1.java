/*
 * @test
 * @summary Tests for java.util.regex.Matcher.end()
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testEndWithMatch();
        testEndWithoutMatch();
    }

    private static void testEndWithMatch() {
        Pattern pattern = Pattern.compile("a*");
        Matcher matcher = pattern.matcher("aaa");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match");
        }
        int endIndex = matcher.end();
        if (endIndex != 3) {
            throw new AssertionError("Expected end index 3, but got " + endIndex);
        }
    }

    private static void testEndWithoutMatch() {
        Pattern pattern = Pattern.compile("a+");
        Matcher matcher = pattern.matcher("b");
        boolean found = matcher.find();
        if (found) {
            throw new AssertionError("Did not expect to find a match");
        }
        try {
            matcher.end();
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
