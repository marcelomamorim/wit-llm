/* @test
   @summary Testa o método end(String name) da classe java.util.regex.Matcher.
   @run main MatcherWitupTest_Pd40650ee6e48
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class MatcherWitupTest_Pd40650ee6e48 {
    public static void main(String[] args) {
        testEndWithNullName();
        testEndWithInvalidGroupName();
        testEndWithValidGroupName();
    }
    private static void testEndWithNullName() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        matcher.find();
        try {
            matcher.end(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
    private static void testEndWithInvalidGroupName() {
        Pattern pattern = Pattern.compile("(?<foo>a)(b)");
        Matcher matcher = pattern.matcher("ab");
        matcher.find();
        String invalidGroupName = "nonexistent";
        try {
            matcher.end(invalidGroupName);
            throw new AssertionError("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
    private static void testEndWithValidGroupName() {
        Pattern pattern = Pattern.compile("(?<foo>a)(b)");
        Matcher matcher = pattern.matcher("ab");
        matcher.find();
        int endIndex = matcher.end("foo");
        if (endIndex != 1) {
            throw new AssertionError("Unexpected end index: " + endIndex);
        }
    }
}
