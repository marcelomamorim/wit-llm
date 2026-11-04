/* @test    @summary Test for Matcher.appendReplacement method behavior and exception handling    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testAppendReplacementWithMatch();
        testAppendReplacementWithoutMatch();
        testAppendReplacementNullReplacement();
    }

    private static void testAppendReplacementWithMatch() {
        Pattern pattern = Pattern.compile("a+");
        Matcher matcher = pattern.matcher("aaabbb");
        StringBuffer sb = new StringBuffer();
        if (matcher.find()) {
            matcher.appendReplacement(sb, "X");
            String result = sb.toString();
            if (!result.equals("X")) {
                throw new AssertionError("Expected 'X', but got: " + result);
            }
        } else {
            throw new AssertionError("Expected to find a match");
        }
    }

    private static void testAppendReplacementWithoutMatch() {
        Pattern pattern = Pattern.compile("z+");
        Matcher matcher = pattern.matcher("aaabbb");
        StringBuffer sb = new StringBuffer();
        boolean exceptionThrown = false;
        try {
            matcher.appendReplacement(sb, "Y");
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException when no match is available");
        }
    }

    private static void testAppendReplacementNullReplacement() {
        Pattern pattern = Pattern.compile("a+");
        Matcher matcher = pattern.matcher("aaa");
        StringBuffer sb = new StringBuffer();
        if (matcher.find()) {
            try {
                matcher.appendReplacement(sb, null);
                throw new AssertionError("Expected NullPointerException for null replacement");
            } catch (NullPointerException e) {
                // Expected exception
            }
        } else {
            throw new AssertionError("Expected to find a match");
        }
    }
}
