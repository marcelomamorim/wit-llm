/*
 * @test
 * @summary Checks StringBuffer.replace(int, int, String) normal behavior, cache invalidation and index-range exceptions
 * @run main StringBufferWitupTest
 */
public class StringBufferWitupTest {

    public static void main(String[] args) {
        testBasicReplace();
        testReplaceAtEndAppends();
        testReplaceClampsEndToLength();
        testReplaceReturnsSameInstance();
        testToStringCacheInvalidated();
        testNegativeStartThrows();
        testEndBeforeStartThrows();
        testStartBeyondLengthThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectSIOOBE(Runnable action, String message) {
        try {
            action.run();
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException(message + ": no StringIndexOutOfBoundsException");
    }

    static void testBasicReplace() {
        StringBuffer sb = new StringBuffer("hello world");
        sb.replace(0, 5, "HELLO");
        check("HELLO world".equals(sb.toString()), "basic replace failed: " + sb);
    }

    static void testReplaceAtEndAppends() {
        StringBuffer sb = new StringBuffer("abc");
        sb.replace(3, 3, "!");
        check("abc!".equals(sb.toString()), "replace at end failed: " + sb);
    }

    static void testReplaceClampsEndToLength() {
        StringBuffer sb = new StringBuffer("abc");
        sb.replace(0, 10, "x");
        check("x".equals(sb.toString()), "end beyond length not clamped: " + sb);
    }

    static void testReplaceReturnsSameInstance() {
        StringBuffer sb = new StringBuffer("abc");
        StringBuffer result = sb.replace(0, 1, "Z");
        check(result == sb, "replace should return this");
        check("Zbc".equals(result.toString()), "unexpected content: " + result);
    }

    static void testToStringCacheInvalidated() {
        StringBuffer sb = new StringBuffer("abc");
        check("abc".equals(sb.toString()), "initial toString failed");
        sb.replace(1, 2, "XY");
        check("aXYc".equals(sb.toString()), "toString stale after replace: " + sb);
    }

    static void testNegativeStartThrows() {
        final StringBuffer sb = new StringBuffer("abc");
        expectSIOOBE(() -> sb.replace(-1, 2, "x"), "negative start");
    }

    static void testEndBeforeStartThrows() {
        final StringBuffer sb = new StringBuffer("abcdef");
        expectSIOOBE(() -> sb.replace(4, 2, "x"), "end < start");
    }

    static void testStartBeyondLengthThrows() {
        final StringBuffer sb = new StringBuffer("abc");
        expectSIOOBE(() -> sb.replace(4, 5, "x"), "start beyond length");
    }
}
