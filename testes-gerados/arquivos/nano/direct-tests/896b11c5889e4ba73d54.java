/* @test    @summary Test for Matcher.group() method to ensure it returns the entire matched substring or throws if no match.    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testGroupReturnsMatch();
        testGroupWithoutMatch();
    }

    static void testGroupReturnsMatch() {
        Pattern pattern = Pattern.compile("\d+");
        Matcher matcher = pattern.matcher("abc123xyz");
        if (matcher.find()) {
            String result = matcher.group();
            if (!"123".equals(result)) {
                throw new AssertionError("Expected '123', but got: " + result);
            }
        } else {
            throw new AssertionError("Expected to find a match but did not.");
        }
    }

    static void testGroupWithoutMatch() {
        Pattern pattern = Pattern.compile("\d+");
        Matcher matcher = pattern.matcher("abcxyz");
        if (matcher.find()) {
            // Should not reach here
            throw new AssertionError("Unexpected match found.");
        }
        try {
            matcher.group();
            throw new AssertionError("Expected IllegalStateException when calling group() without match.");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
