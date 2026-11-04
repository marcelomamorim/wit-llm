/*
 * @test
 * @summary Verify StringBuffer.deleteCharAt removes the right character, returns this, invalidates the cached toString, and rejects out-of-range indices
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testDeleteMiddle();
        testDeleteLast();
        testReturnsSameInstance();
        testCacheInvalidated();
        testOutOfRange();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDeleteMiddle() {
        StringBuffer sb = new StringBuffer("abcd");
        sb.deleteCharAt(1);
        check("acd".equals(sb.toString()), "expected acd but was " + sb);
        check(sb.length() == 3, "expected length 3 but was " + sb.length());
    }

    static void testDeleteLast() {
        StringBuffer sb = new StringBuffer("abc");
        sb.deleteCharAt(2);
        check("ab".equals(sb.toString()), "expected ab but was " + sb);
    }

    static void testReturnsSameInstance() {
        StringBuffer sb = new StringBuffer("xy");
        StringBuffer result = sb.deleteCharAt(0);
        check(result == sb, "deleteCharAt must return this");
        check("y".equals(result.toString()), "expected y but was " + result);
    }

    static void testCacheInvalidated() {
        StringBuffer sb = new StringBuffer("hello");
        String first = sb.toString();
        check("hello".equals(first), "expected hello but was " + first);
        sb.deleteCharAt(0);
        check("ello".equals(sb.toString()), "stale cache after first delete: " + sb);
        sb.deleteCharAt(sb.length() - 1);
        check("ell".equals(sb.toString()), "stale cache after second delete: " + sb);
    }

    static void testOutOfRange() {
        StringBuffer sb = new StringBuffer("ab");
        int[] invalid = {-1, 2, 100};
        for (int index : invalid) {
            boolean thrown = false;
            try {
                sb.deleteCharAt(index);
            } catch (StringIndexOutOfBoundsException e) {
                thrown = true;
            }
            check(thrown, "expected StringIndexOutOfBoundsException for index " + index);
        }
        check("ab".equals(sb.toString()), "buffer changed after failed deletes: " + sb);

        StringBuffer empty = new StringBuffer();
        boolean thrownEmpty = false;
        try {
            empty.deleteCharAt(0);
        } catch (StringIndexOutOfBoundsException e) {
            thrownEmpty = true;
        }
        check(thrownEmpty, "expected StringIndexOutOfBoundsException on empty buffer");
    }
}
