/* @test    @summary Test for Matcher.appendTail to verify IndexOutOfBoundsException scenarios    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testAppendTailWithValidInput();
        testAppendTailWithNullText();
        testAppendTailWithInvalidRange();
    }

    private static void testAppendTailWithValidInput() {
        Pattern pattern = Pattern.compile("a*");
        Matcher matcher = pattern.matcher("aaa");
        StringBuilder sb = new StringBuilder();
        matcher.region(0, 3);
        // Move matcher to end to simulate previous match
        matcher.find();
        try {
            StringBuilder result = matcher.appendTail(sb);
            if (result != sb) {
                throw new AssertionError("Expected result to be the same StringBuilder instance");
            }
            if (!result.toString().equals("aaa")) {
                throw new AssertionError("Unexpected appendTail result: " + result.toString());
            }
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected IndexOutOfBoundsException in valid input test", e);
        }
    }

    private static void testAppendTailWithNullText() {
        Pattern pattern = Pattern.compile("a*");
        Matcher matcher = pattern.matcher("aaa");
        StringBuilder sb = null;
        try {
            matcher.appendTail(sb);
            throw new AssertionError("Expected NullPointerException when passing null StringBuilder");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected IndexOutOfBoundsException when passing null StringBuilder", e);
        }
    }

    private static void testAppendTailWithInvalidRange() {
        Pattern pattern = Pattern.compile("a*");
        Matcher matcher = pattern.matcher("aaa");
        StringBuilder sb = new StringBuilder();
        // Manually set lastAppendPosition to an invalid value to simulate error
        // Note: lastAppendPosition is private; so we simulate by calling appendTail without prior find()
        // which should cause IndexOutOfBoundsException due to no match
        try {
            matcher.appendTail(sb);
            throw new AssertionError("Expected IndexOutOfBoundsException due to invalid region");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
