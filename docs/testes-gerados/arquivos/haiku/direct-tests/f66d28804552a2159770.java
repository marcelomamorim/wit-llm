/*
 * @test
 * @summary Behavioral tests for StringBuffer.delete(int start, int end)
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testDeleteMiddleRange();
        testDeleteReturnsSameInstance();
        testEndBeyondLengthIsClamped();
        testEmptyRangeLeavesContentUnchanged();
        testDeleteWholeContent();
        testToStringCacheIsInvalidated();
        testNegativeStartThrows();
        testStartGreaterThanEndThrows();
        testStartBeyondLengthThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDeleteMiddleRange() {
        StringBuffer sb = new StringBuffer("abcdef");
        sb.delete(2, 4);
        check("abef".equals(sb.toString()), "expected abef but was " + sb);
        check(sb.length() == 4, "expected length 4");
    }

    static void testDeleteReturnsSameInstance() {
        StringBuffer sb = new StringBuffer("abc");
        StringBuffer result = sb.delete(0, 1);
        check(result == sb, "delete must return this");
        check("bc".equals(sb.toString()), "expected bc");
    }

    static void testEndBeyondLengthIsClamped() {
        StringBuffer sb = new StringBuffer("abcdef");
        sb.delete(2, 100);
        check("ab".equals(sb.toString()), "expected ab but was " + sb);
    }

    static void testEmptyRangeLeavesContentUnchanged() {
        StringBuffer sb = new StringBuffer("abc");
        sb.delete(1, 1);
        check("abc".equals(sb.toString()), "expected abc unchanged");
    }

    static void testDeleteWholeContent() {
        StringBuffer sb = new StringBuffer("abcdef");
        sb.delete(0, 6);
        check(sb.length() == 0, "expected empty buffer");
        check("".equals(sb.toString()), "expected empty string");
    }

    static void testToStringCacheIsInvalidated() {
        StringBuffer sb = new StringBuffer("hello");
        check("hello".equals(sb.toString()), "initial toString failed");
        sb.delete(0, 2);
        check("llo".equals(sb.toString()), "toString returned stale content: " + sb);
    }

    static void testNegativeStartThrows() {
        StringBuffer sb = new StringBuffer("abc");
        boolean thrown = false;
        try {
            sb.delete(-1, 2);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected StringIndexOutOfBoundsException for negative start");
    }

    static void testStartGreaterThanEndThrows() {
        StringBuffer sb = new StringBuffer("abc");
        boolean thrown = false;
        try {
            sb.delete(2, 1);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected StringIndexOutOfBoundsException when start > end");
    }

    static void testStartBeyondLengthThrows() {
        StringBuffer sb = new StringBuffer("abc");
        boolean thrown = false;
        try {
            sb.delete(5, 6);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected StringIndexOutOfBoundsException when start > length");
    }
}
