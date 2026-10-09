/* @test
   @summary Tests the group(String name) method of java.util.regex.Matcher.
   @run main MatcherWitupTest_P344dca8328ac
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Objects;

public class MatcherWitupTest_P344dca8328ac {
    public static void main(String[] args) throws Exception {
        testGroupWithExistingGroupName();
        testGroupWithNonExistingGroupName();
        testGroupWithNullName();
    }

    private static void testGroupWithExistingGroupName() {
        Pattern pattern = Pattern.compile("(?<name>abc)(?<other>def)");
        Matcher matcher = pattern.matcher("abcdef");
        if (matcher.find()) {
            String result = matcher.group("name");
            if (!"abc".equals(result)) {
                throw new AssertionError("Expected 'abc', got: " + result);
            }
        } else {
            throw new AssertionError("Matcher did not find a match");
        }
    }

    private static void testGroupWithNonExistingGroupName() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.find()) {
            String result = matcher.group("nonexistent");
            if (result != null) {
                throw new AssertionError("Expected null for non-existent group, got: " + result);
            }
        } else {
            throw new AssertionError("Matcher did not find a match");
        }
    }

    private static void testGroupWithNullName() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.find()) {
            try {
                matcher.group((String) null);
                throw new AssertionError("Expected NullPointerException for null group name");
            } catch (NullPointerException e) {
                // expected
            }
        } else {
            throw new AssertionError("Matcher did not find a match");
        }
    }
}
