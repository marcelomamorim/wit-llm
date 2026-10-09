/* @test    @summary Tests for java.util.regex.Matcher.start(String) method.    @run main MatcherWitupTest_P95cd781054f9 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P95cd781054f9 {

    public static void main(String[] args) {
        testStartWithNullName();
        testStartWithInvalidGroupName();
        testStartWithValidGroupName();
    }

    private static void testStartWithNullName() {
        try {
            Pattern pattern = Pattern.compile("(a)(b)");
            Matcher matcher = pattern.matcher("ab");
            matcher.find();
            matcher.start(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testStartWithInvalidGroupName() {
        try {
            Pattern pattern = Pattern.compile("(?<foo>a)");
            Matcher matcher = pattern.matcher("a");
            matcher.find();
            matcher.start("nonexistentGroup");
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testStartWithValidGroupName() {
        try {
            Pattern pattern = Pattern.compile("(?<foo>a)(b)");
            Matcher matcher = pattern.matcher("ab");
            matcher.find();
            int startIndex = matcher.start("foo");
            if (startIndex != 0) {
                throw new AssertionError("start(" + "foo" + ") returned " + startIndex + ", expected 0");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
