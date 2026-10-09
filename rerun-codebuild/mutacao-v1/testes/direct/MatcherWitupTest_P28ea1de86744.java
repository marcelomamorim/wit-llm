/* @test    @summary Test for Matcher.appendReplacement method behavior and exception handling    @run main MatcherWitupTest_P28ea1de86744 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P28ea1de86744 {
    public static void main(String[] args) {
        testAppendReplacementWithNoMatch();
        testAppendReplacementWithMatch();
        testAppendReplacementWithNoMatchState();
    }

    private static void testAppendReplacementWithNoMatch() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("def");
        StringBuilder sb = new StringBuilder();
        boolean exceptionThrown = false;
        try {
            matcher.appendReplacement(sb, "xyz");
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException when no match is available");
        }
    }

    private static void testAppendReplacementWithMatch() {
        Pattern pattern = Pattern.compile("(\d+)");
        Matcher matcher = pattern.matcher("Order 1234 confirmed");
        StringBuilder sb = new StringBuilder();
        if (matcher.find()) {
            matcher.appendReplacement(sb, "Number: $1");
            String result = sb.toString();
            if (!result.contains("Number: 1234")) {
                throw new AssertionError("Replacement did not produce expected output: " + result);
            }
        } else {
            throw new AssertionError("Pattern did not find a match");
        }
    }

    private static void testAppendReplacementWithNoMatchState() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("def");
        StringBuilder sb = new StringBuilder();
        // Call find() to set match state to no match
        matcher.find();
        boolean exceptionThrown = false;
        try {
            matcher.appendReplacement(sb, "xyz");
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException when no match is available after find()");
        }
    }
}
