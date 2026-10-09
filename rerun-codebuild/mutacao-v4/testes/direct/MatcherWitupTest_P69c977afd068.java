/* @test
   @summary Verifies the behavior of Matcher.appendReplacement method.
   @run main MatcherWitupTest_P69c977afd068
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Objects;

public class MatcherWitupTest_P69c977afd068 {
    public static void main(String[] args) {
        testAppendReplacementWithMatch();
        testAppendReplacementWithoutMatch();
        testAppendReplacementWithNullReplacement();
    }

    private static void testAppendReplacementWithMatch() {
        Pattern pattern = Pattern.compile("a+");
        Matcher matcher = pattern.matcher("aaabbb");
        StringBuilder sb = new StringBuilder();
        if (matcher.find()) {
            Matcher resultMatcher = matcher.appendReplacement(sb, "X");
            String expected = "X";
            String actual = sb.toString();
            if (!Objects.equals(actual, expected)) {
                throw new AssertionError("Expected: '" + expected + "', but got: '" + actual + "'");
            }
            if (resultMatcher != matcher) {
                throw new AssertionError("appendReplacement should return the same matcher instance");
            }
        } else {
            throw new AssertionError("Expected to find a match");
        }
    }

    private static void testAppendReplacementWithoutMatch() {
        Pattern pattern = Pattern.compile("b+");
        Matcher matcher = pattern.matcher("aaa");
        StringBuilder sb = new StringBuilder();
        try {
            matcher.appendReplacement(sb, "X");
            throw new AssertionError("Expected IllegalStateException when no match is available");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    private static void testAppendReplacementWithNullReplacement() {
        Pattern pattern = Pattern.compile("a+");
        Matcher matcher = pattern.matcher("aaa");
        StringBuilder sb = new StringBuilder();
        if (matcher.find()) {
            try {
                matcher.appendReplacement(sb, null);
                throw new AssertionError("Expected NullPointerException for null replacement");
            } catch (NullPointerException e) {
                // expected
            }
        } else {
            throw new AssertionError("Expected to find a match");
        }
    }
}
