/* @test    @summary Test for Matcher.group(String name) method in java.util.regex.Matcher    @run main MatcherWitupTest_P9d3b03ffa4ff */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P9d3b03ffa4ff {

    public static void main(String[] args) {
        testGroupWithExistingGroupName();
        testGroupWithNonExistingGroupName();
        testGroupWithNullName();
    }

    private static void testGroupWithExistingGroupName() {
        Pattern pattern = Pattern.compile("(?<word>\w+)");
        Matcher matcher = pattern.matcher("hello world");
        if (matcher.find()) {
            String result = matcher.group("word");
            if (!"hello".equals(result)) {
                throw new AssertionError("Expected 'hello', but got: " + result);
            }
        } else {
            throw new AssertionError("Matcher did not find a match");
        }
    }

    private static void testGroupWithNonExistingGroupName() {
        Pattern pattern = Pattern.compile("(?<word>\w+)");
        Matcher matcher = pattern.matcher("hello world");
        if (matcher.find()) {
            String result = matcher.group("nonexistent");
            if (result != null) {
                throw new AssertionError("Expected null for non-existent group, but got: " + result);
            }
        } else {
            throw new AssertionError("Matcher did not find a match");
        }
    }

    private static void testGroupWithNullName() {
        Pattern pattern = Pattern.compile("\w+");
        Matcher matcher = pattern.matcher("hello world");
        if (matcher.find()) {
            try {
                matcher.group(null);
                throw new AssertionError("Expected NullPointerException for null group name");
            } catch (NullPointerException e) {
                // Expected exception
            }
        } else {
            throw new AssertionError("Matcher did not find a match");
        }
    }
}
