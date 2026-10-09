/* @test
   @summary Verifies the behavior of Matcher.end(String name) method.
   @run main MatcherWitupTest_Pd40650ee6e48
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Objects;

public class MatcherWitupTest_Pd40650ee6e48 {
    public static void main(String[] args) throws Exception {
        testEndWithValidGroupName();
        testEndWithInvalidGroupName();
    }

    private static void testEndWithValidGroupName() {
        Pattern pattern = Pattern.compile("(?<foo>abc)(?<bar>def)");
        Matcher matcher = pattern.matcher("abcdef");
        if (matcher.find()) {
            int endFoo = matcher.end("foo");
            int endBar = matcher.end("bar");
            if (endFoo != 3) {
                throw new AssertionError("Expected end of group 'foo' to be 3, but was " + endFoo);
            }
            if (endBar != 6) {
                throw new AssertionError("Expected end of group 'bar' to be 6, but was " + endBar);
            }
        } else {
            throw new AssertionError("Matcher did not find a match");
        }
    }

    private static void testEndWithInvalidGroupName() {
        Pattern pattern = Pattern.compile("(abc)");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.find()) {
            try {
                matcher.end("nonexistent");
                throw new AssertionError("Expected IllegalArgumentException for invalid group name");
            } catch (IllegalArgumentException e) {
                // expected
            }
        } else {
            throw new AssertionError("Matcher did not find a match");
        }
    }
}
