/* @test    @summary Tests for java.util.regex.Matcher.appendReplacement method.    @run main MatcherWitupTest_P28ea1de86744 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P28ea1de86744 {

    public static void main(String[] args) {
        testAppendReplacementNoMatch();
        testAppendReplacementInvalidState();
    }

    private static void testAppendReplacementNoMatch() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("bar");
        StringBuilder sb = new StringBuilder();
        try {
            matcher.appendReplacement(sb, "replacement");
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }

    private static void testAppendReplacementInvalidState() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foo bar");
        if (matcher.find()) {
            StringBuilder sb = new StringBuilder();
            // Set first to a valid match start
            matcher.region(0, 10);
            // Manually set first to -1 to simulate invalid state
            // Note: Cannot directly set 'first' as it's private; simulate by not calling find()
            // Instead, test the behavior when no match has been found
            matcher.reset();
            try {
                matcher.appendReplacement(sb, "replacement");
                throw new AssertionError("Expected IllegalStateException was not thrown");
            } catch (IllegalStateException e) {
                // Expected exception
            }
        } else {
            throw new RuntimeException("Matcher did not find expected match");
        }
    }
}
