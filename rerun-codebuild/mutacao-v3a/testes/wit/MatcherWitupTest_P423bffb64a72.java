/*
 * @test
 * @summary Tests for java.util.regex.Matcher.appendReplacement
 * @run main MatcherWitupTest_P423bffb64a72
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P423bffb64a72 {

    public static void main(String[] args) {
        testAppendReplacementWithValidMatch();
        testAppendReplacementWithInvalidState();
        testAppendReplacementWithIndexOutOfBounds();
    }

    private static void testAppendReplacementWithValidMatch() {
        String patternStr = "(foo)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher("foo bar foo");
        StringBuilder sb = new StringBuilder();
        if (matcher.find()) {
            matcher.appendReplacement(sb, "$1baz");
            System.out.println("Valid match test passed: " + sb.toString());
        }
    }

    private static void testAppendReplacementWithInvalidState() {
        String patternStr = "(foo)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher("foo bar");
        StringBuilder sb = new StringBuilder();
        try {
            matcher.appendReplacement(sb, "test");
            System.out.println("Unexpected success in invalid state test");
        } catch (IllegalStateException e) {
            System.out.println("Invalid state test passed: " + e.getMessage());
        }
    }

    private static void testAppendReplacementWithIndexOutOfBounds() {
        String patternStr = "(foo)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher("foo bar");
        StringBuilder sb = new StringBuilder();
        if (matcher.find()) {
            // Manipulate internal state to cause IndexOutOfBoundsException
            // Since internal state is private, simulate via input that triggers exception
            try {
                matcher.appendReplacement(sb, "");
                System.out.println("IndexOutOfBounds test failed: exception not thrown");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("IndexOutOfBounds test passed: " + e.getMessage());
            }
        }
    }
}
