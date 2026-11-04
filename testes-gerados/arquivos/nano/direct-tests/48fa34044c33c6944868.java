/* @test    @summary Test for Matcher.start(int group) method in java.util.regex.Matcher    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testStartWithMatch();
        testStartWithoutMatch();
        testStartInvalidGroupNegative();
        testStartInvalidGroupTooLarge();
    }

    static void testStartWithMatch() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match");
        }
        int startGroup0 = matcher.start(0);
        int startGroup1 = matcher.start(1);
        int startGroup2 = matcher.start(2);
        if (startGroup0 != 0) {
            throw new AssertionError("Expected start(0) to be 0, but was " + startGroup0);
        }
        if (startGroup1 != 0) {
            throw new AssertionError("Expected start(1) to be 0, but was " + startGroup1);
        }
        if (startGroup2 != 1) {
            throw new AssertionError("Expected start(2) to be 1, but was " + startGroup2);
        }
    }

    static void testStartWithoutMatch() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("xyz");
        boolean found = matcher.find();
        if (found) {
            throw new AssertionError("Did not expect to find a match");
        }
        try {
            matcher.start(0);
            throw new AssertionError("Expected IllegalStateException for start(0) without match");
        } catch (IllegalStateException e) {
            // expected
        }
        try {
            matcher.start(1);
            throw new AssertionError("Expected IllegalStateException for start(1) without match");
        } catch (IllegalStateException e) {
            // expected
        }
        try {
            matcher.start(2);
            throw new AssertionError("Expected IllegalStateException for start(2) without match");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    static void testStartInvalidGroupNegative() {
        Pattern pattern = Pattern.compile("(a)");
        Matcher matcher = pattern.matcher("a");
        matcher.find();
        try {
            matcher.start(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative group index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testStartInvalidGroupTooLarge() {
        Pattern pattern = Pattern.compile("(a)");
        Matcher matcher = pattern.matcher("a");
        matcher.find();
        int groupCount = matcher.groupCount();
        try {
            matcher.start(groupCount + 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for too large group index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
