/* @test    @summary Test for Matcher.end(int group) method behavior and exception handling    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testEndWithValidGroup();
        testEndWithInvalidGroupNegative();
        testEndWithInvalidGroupTooLarge();
        testEndWithoutMatch();
    }

    static void testEndWithValidGroup() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match");
        }
        int endGroup1 = matcher.end(1);
        int endGroup2 = matcher.end(2);
        int endOverall = matcher.end();
        if (endGroup1 != 1) {
            throw new AssertionError("Expected end(1) to be 1, but was " + endGroup1);
        }
        if (endGroup2 != 2) {
            throw new AssertionError("Expected end(2) to be 2, but was " + endGroup2);
        }
        if (endOverall != 2) {
            throw new AssertionError("Expected end() to be 2, but was " + endOverall);
        }
    }

    static void testEndWithInvalidGroupNegative() {
        Pattern pattern = Pattern.compile("(a)");
        Matcher matcher = pattern.matcher("a");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match");
        }
        try {
            matcher.end(-1);
            throw new AssertionError("Expected IllegalStateException for negative group index");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    static void testEndWithInvalidGroupTooLarge() {
        Pattern pattern = Pattern.compile("(a)");
        Matcher matcher = pattern.matcher("a");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match");
        }
        int groupCount = matcher.groupCount();
        try {
            matcher.end(groupCount + 1);
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testEndWithoutMatch() {
        Pattern pattern = Pattern.compile("a");
        Matcher matcher = pattern.matcher("b");
        if (matcher.find()) {
            throw new AssertionError("Did not expect to find a match");
        }
        try {
            matcher.end(0);
            throw new AssertionError("Expected IllegalStateException when no match is available");
        } catch (IllegalStateException e) {
            // expected
        }
    }
}
