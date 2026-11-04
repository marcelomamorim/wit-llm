/* @test    @summary Test for Matcher.appendReplacement method behavior.    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testAppendReplacementWithMatch();
        testAppendReplacementWithoutMatch();
        testAppendReplacementWithNullReplacement();
    }

    static void testAppendReplacementWithMatch() {
        Pattern pattern = Pattern.compile("a+");
        Matcher matcher = pattern.matcher("aaabbb");
        StringBuilder sb = new StringBuilder();
        if (matcher.find()) {
            matcher.appendReplacement(sb, "X");
            assert sb.toString().equals("X");
        } else {
            throw new AssertionError("Expected match not found");
        }
    }

    static void testAppendReplacementWithoutMatch() {
        Pattern pattern = Pattern.compile("z+");
        Matcher matcher = pattern.matcher("abc");
        StringBuilder sb = new StringBuilder();
        try {
            matcher.appendReplacement(sb, "Y");
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }

    static void testAppendReplacementWithNullReplacement() {
        Pattern pattern = Pattern.compile("a+");
        Matcher matcher = pattern.matcher("aaa");
        StringBuilder sb = new StringBuilder();
        if (matcher.find()) {
            try {
                matcher.appendReplacement(sb, null);
                throw new AssertionError("Expected NullPointerException not thrown");
            } catch (NullPointerException e) {
                // Expected exception
            }
        } else {
            throw new AssertionError("Expected match not found");
        }
    }
}
