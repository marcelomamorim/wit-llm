/*
 * @test
 * @summary Witup-generated checks for StringBuffer.deleteCharAt
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testDeleteMiddle();
        testDeleteFirstAndLast();
        testReturnsSameInstance();
        testToStringCacheInvalidated();
        expectStringIndexOutOfBounds(new StringBuffer("abc"), -1);
        expectStringIndexOutOfBounds(new StringBuffer("abc"), 3);
        expectStringIndexOutOfBounds(new StringBuffer(), 0);
    }

    static void testDeleteMiddle() {
        StringBuffer sb = new StringBuffer("abc");
        sb.deleteCharAt(1);
        check("ac".equals(sb.toString()), "expected ac but was " + sb);
    }

    static void testDeleteFirstAndLast() {
        StringBuffer sb = new StringBuffer("a");
        sb.deleteCharAt(0);
        check(sb.length() == 0, "expected empty buffer");

        StringBuffer sb2 = new StringBuffer("xyz");
        sb2.deleteCharAt(2);
        check("xy".equals(sb2.toString()), "expected xy but was " + sb2);
    }

    static void testReturnsSameInstance() {
        StringBuffer sb = new StringBuffer("abc");
        StringBuffer result = sb.deleteCharAt(0);
        check(result == sb, "deleteCharAt must return this");
    }

    static void testToStringCacheInvalidated() {
        StringBuffer sb = new StringBuffer("abc");
        check("abc".equals(sb.toString()), "initial toString mismatch");
        sb.deleteCharAt(0);
        check("bc".equals(sb.toString()), "stale toString after deleteCharAt: " + sb);
    }

    static void expectStringIndexOutOfBounds(StringBuffer sb, int index) {
        String before = sb.toString();
        try {
            sb.deleteCharAt(index);
            throw new RuntimeException("expected StringIndexOutOfBoundsException for index " + index);
        } catch (StringIndexOutOfBoundsException e) {
            check(before.equals(sb.toString()), "buffer modified on failed deleteCharAt");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
