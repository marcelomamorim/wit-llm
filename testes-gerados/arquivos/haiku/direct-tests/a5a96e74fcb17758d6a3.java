/*
 * @test
 * @summary Verifies Matcher.appendTail(StringBuilder) appends the unmatched tail of the input and returns the same builder.
 * @run main MatcherWitupTest
 */

public class MatcherWitupTest {

    public static void main(String[] args) {
        testTailAfterReplacement();
        testTailWithoutAnyAppend();
        testReturnsSameBuilder();
        testEmptyInput();
        testPrefixPreserved();
    }

    static void testTailAfterReplacement() {
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("a").matcher("xay");
        check(m.find(), "find should succeed");
        StringBuilder sb = new StringBuilder();
        m.appendReplacement(sb, "b");
        m.appendTail(sb);
        check(sb.toString().equals("xby"), "expected xby but was " + sb);
    }

    static void testTailWithoutAnyAppend() {
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("b").matcher("abc");
        check(m.find(), "find should succeed");
        StringBuilder sb = new StringBuilder();
        m.appendTail(sb);
        check(sb.toString().equals("abc"), "expected abc but was " + sb);
    }

    static void testReturnsSameBuilder() {
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("z").matcher("hello");
        StringBuilder sb = new StringBuilder("pre-");
        StringBuilder result = m.appendTail(sb);
        check(result == sb, "appendTail should return the same StringBuilder instance");
        check(sb.toString().equals("pre-hello"), "expected pre-hello but was " + sb);
    }

    static void testEmptyInput() {
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("a").matcher("");
        StringBuilder sb = new StringBuilder();
        m.appendTail(sb);
        check(sb.length() == 0, "expected empty result but was " + sb);
    }

    static void testPrefixPreserved() {
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("\\d+").matcher("ab12cd");
        check(m.find(), "find should succeed");
        StringBuilder sb = new StringBuilder("[");
        m.appendReplacement(sb, "#");
        m.appendTail(sb);
        check(sb.toString().equals("[ab#cd"), "expected [ab#cd but was " + sb);
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
