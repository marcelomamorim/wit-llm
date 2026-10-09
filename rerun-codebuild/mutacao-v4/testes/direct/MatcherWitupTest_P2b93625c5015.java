/* @test
   @summary Verifies the behavior of the start(String name) method in Matcher.
   @run main MatcherWitupTest_P2b93625c5015
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Objects;

public class MatcherWitupTest_P2b93625c5015 {
    public static void main(String[] args) {
        testStartWithNamedGroup();
        testStartWithNonexistentGroup();
    }

    private static void testStartWithNamedGroup() {
        Pattern pattern = Pattern.compile("(?<foo>abc)(?<bar>def)");
        Matcher matcher = pattern.matcher("abcdef");
        if (matcher.find()) {
            int startFoo = matcher.start("foo");
            int startBar = matcher.start("bar");
            if (startFoo != 0) {
                throw new AssertionError("Expected start of 'foo' group to be 0, but was " + startFoo);
            }
            if (startBar != 3) {
                throw new AssertionError("Expected start of 'bar' group to be 3, but was " + startBar);
            }
        } else {
            throw new AssertionError("Matcher did not find a match");
        }
    }

    private static void testStartWithNonexistentGroup() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.find()) {
            try {
                matcher.start("nonexistent");
                throw new AssertionError("Expected IllegalArgumentException for nonexistent group");
            } catch (IllegalArgumentException e) {
                // expected
            }
        } else {
            throw new AssertionError("Matcher did not find a match");
        }
    }
}
