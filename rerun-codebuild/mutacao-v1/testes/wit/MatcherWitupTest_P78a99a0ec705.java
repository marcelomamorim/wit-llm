/* @test    @summary Test for java.util.regex.Matcher.group(String) method.    @run main MatcherWitupTest_P78a99a0ec705 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P78a99a0ec705 {

    public static void main(String[] args) {
        testGroupWithNullName();
        testGroupWithInvalidName();
    }

    private static void testGroupWithNullName() {
        String patternStr = "(?<name>abc)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher("abc");
        if (!matcher.matches()) {
            throw new AssertionError("Matcher should match the input string.");
        }
        try {
            matcher.group(null);
            throw new AssertionError("Expected NullPointerException for null group name.");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testGroupWithInvalidName() {
        String patternStr = "(?<name>abc)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher("abc");
        if (!matcher.matches()) {
            throw new AssertionError("Matcher should match the input string.");
        }
        String invalidName = "nonexistent";
        try {
            matcher.group(invalidName);
            throw new AssertionError("Expected IllegalArgumentException for invalid group name.");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
