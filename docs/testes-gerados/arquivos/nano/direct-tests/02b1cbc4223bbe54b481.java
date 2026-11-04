/* @test    @summary Test for Matcher.end(int group) method behavior.    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testEndWithValidGroup();
        testEndWithInvalidGroup();
    }

    private static void testEndWithValidGroup() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match.");
        }
        int endGroup0 = matcher.end(0);
        int endGroup1 = matcher.end(1);
        int endGroup2 = matcher.end(2);
        if (endGroup0 != 2) {
            throw new AssertionError("Expected end(0) to be 2, but was " + endGroup0);
        }
        if (endGroup1 != 1) {
            throw new AssertionError("Expected end(1) to be 1, but was " + endGroup1);
        }
        if (endGroup2 != 2) {
            throw new AssertionError("Expected end(2) to be 2, but was " + endGroup2);
        }
    }

    private static void testEndWithInvalidGroup() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match.");
        }
        try {
            matcher.end(3);
            throw new AssertionError("Expected IndexOutOfBoundsException for invalid group.");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            matcher.end(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative group.");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
