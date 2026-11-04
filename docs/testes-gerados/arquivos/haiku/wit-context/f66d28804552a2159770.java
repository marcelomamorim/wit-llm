/*
 * @test
 * @summary Behavioral tests for StringBuffer.delete(int, int) covering normal deletion, clamping, invalid ranges and return identity
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testNormalDelete();
        testEmptyRangeIsNoOp();
        testEndBeyondLengthIsClamped();
        testReturnsSameInstance();
        testNegativeStartThrows();
        testEndBeforeStartThrows();
        testStartBeyondLengthThrows();
        testFailedDeleteLeavesContentUnchanged();
        testToStringReflectsDeleteAfterPriorToString();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNormalDelete() {
        StringBuffer sb = new StringBuffer("abcdef");
        sb.delete(0, 3);
        check("def".equals(sb.toString()), "delete(0,3) expected 'def' but was '" + sb + "'");
        check(sb.length() == 3, "length after delete expected 3");
    }

    static void testEmptyRangeIsNoOp() {
        StringBuffer sb = new StringBuffer("abcdef");
        sb.delete(2, 2);
        check("abcdef".equals(sb.toString()), "delete(2,2) should not change content");
    }

    static void testEndBeyondLengthIsClamped() {
        StringBuffer sb = new StringBuffer("abcdef");
        sb.delete(2, 100);
        check("ab".equals(sb.toString()), "delete(2,100) expected 'ab' but was '" + sb + "'");
    }

    static void testReturnsSameInstance() {
        StringBuffer sb = new StringBuffer("abcdef");
        StringBuffer result = sb.delete(1, 2);
        check(result == sb, "delete must return this");
        check("acdef".equals(result.toString()), "delete(1,2) expected 'acdef'");
    }

    static void testNegativeStartThrows() {
        StringBuffer sb = new StringBuffer("abcdef");
        boolean thrown = false;
        try {
            sb.delete(-1, 2);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "delete(-1,2) should throw StringIndexOutOfBoundsException");
    }

    static void testEndBeforeStartThrows() {
        StringBuffer sb = new StringBuffer("abcdef");
        boolean thrown = false;
        try {
            sb.delete(4, 2);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "delete(4,2) should throw StringIndexOutOfBoundsException");
    }

    static void testStartBeyondLengthThrows() {
        StringBuffer sb = new StringBuffer("abcdef");
        boolean thrown = false;
        try {
            sb.delete(7, 8);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "delete(7,8) on length 6 should throw StringIndexOutOfBoundsException");
    }

    static void testFailedDeleteLeavesContentUnchanged() {
        StringBuffer sb = new StringBuffer("abcdef");
        try {
            sb.delete(4, 2);
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        check("abcdef".equals(sb.toString()), "failed delete must not modify content");
    }

    static void testToStringReflectsDeleteAfterPriorToString() {
        StringBuffer sb = new StringBuffer("abcdef");
        String before = sb.toString();
        check("abcdef".equals(before), "initial toString mismatch");
        sb.delete(0, 2);
        check("cdef".equals(sb.toString()), "toString after delete must reflect change");
    }
}
