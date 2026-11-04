/*
 * @test
 * @summary Verify StringBuilder.append(CharSequence, int, int) appends the subsequence [start, end) and returns this
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testReturnsThisAndAppendsSubsequence();
        testFullRange();
        testEmptyRangeLeavesContentUnchanged();
        testStartAtEndAppendsNothing();
        testStringBuilderAsSource();
        testSmallCapacityGrows();
        testChainedCalls();
    }

    static void testReturnsThisAndAppendsSubsequence() {
        StringBuilder sb = new StringBuilder();
        StringBuilder ret = sb.append("hello", 1, 3);
        check(ret == sb, "append must return this");
        check("el".equals(sb.toString()), "expected \"el\" but was \"" + sb + "\"");
    }

    static void testFullRange() {
        StringBuilder sb = new StringBuilder("x");
        sb.append("abcdef", 0, 6);
        check("xabcdef".equals(sb.toString()), "full range append failed: " + sb);
    }

    static void testEmptyRangeLeavesContentUnchanged() {
        StringBuilder sb = new StringBuilder("base");
        sb.append("abc", 1, 1);
        sb.append("abc", 0, 0);
        check("base".equals(sb.toString()), "empty range must not change content: " + sb);
    }

    static void testStartAtEndAppendsNothing() {
        StringBuilder sb = new StringBuilder("q");
        sb.append("abc", 3, 3);
        check("q".equals(sb.toString()), "append at end of source must add nothing: " + sb);
    }

    static void testStringBuilderAsSource() {
        StringBuilder sb = new StringBuilder();
        sb.append(new StringBuilder("12345"), 2, 4);
        check("34".equals(sb.toString()), "expected \"34\" but was \"" + sb + "\"");
    }

    static void testSmallCapacityGrows() {
        StringBuilder sb = new StringBuilder(1);
        sb.append("ABCDEFGH", 0, 8);
        check("ABCDEFGH".equals(sb.toString()), "append must grow buffer: " + sb);
        check(sb.length() == 8, "length expected 8 but was " + sb.length());
    }

    static void testChainedCalls() {
        StringBuilder sb = new StringBuilder();
        sb.append("ab", 0, 1).append("cd", 1, 2);
        check("ad".equals(sb.toString()), "chained append failed: " + sb);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
