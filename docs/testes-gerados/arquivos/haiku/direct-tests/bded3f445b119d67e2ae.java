/*
 * @test
 * @summary Verifies StringBuffer.setLength updates length and observable content, including after toString caching
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testTruncateAfterToStringCache();
        testExtendWithNulChars();
        testSetLengthZero();
        testAppendAfterTruncate();
        testEmptyBufferSetLengthZero();
    }

    static void testTruncateAfterToStringCache() {
        StringBuffer sb = new StringBuffer("hello");
        check("hello".equals(sb.toString()), "initial toString");
        sb.setLength(3);
        check(sb.length() == 3, "length after truncate");
        check("hel".equals(sb.toString()), "toString after truncate must not return stale cache");
    }

    static void testExtendWithNulChars() {
        StringBuffer sb = new StringBuffer("hi");
        check("hi".equals(sb.toString()), "initial toString");
        sb.setLength(5);
        check(sb.length() == 5, "length after extend");
        check(sb.charAt(0) == 'h' && sb.charAt(1) == 'i', "prefix preserved");
        check(sb.charAt(2) == '\0' && sb.charAt(4) == '\0', "extended chars are NUL");
        check("hi\0\0\0".equals(sb.toString()), "toString after extend must reflect new length");
    }

    static void testSetLengthZero() {
        StringBuffer sb = new StringBuffer("abc");
        check("abc".equals(sb.toString()), "initial toString");
        sb.setLength(0);
        check(sb.length() == 0, "length zero");
        check("".equals(sb.toString()), "toString empty after setLength(0)");
    }

    static void testAppendAfterTruncate() {
        StringBuffer sb = new StringBuffer("ab");
        check("ab".equals(sb.toString()), "initial toString");
        sb.setLength(1);
        sb.append("z");
        check("az".equals(sb.toString()), "append after truncate");
        check(sb.length() == 2, "length after append");
    }

    static void testEmptyBufferSetLengthZero() {
        StringBuffer sb = new StringBuffer();
        sb.setLength(0);
        check(sb.length() == 0, "empty buffer length");
        check("".equals(sb.toString()), "empty buffer toString");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Assertion failed: " + message);
        }
    }
}
