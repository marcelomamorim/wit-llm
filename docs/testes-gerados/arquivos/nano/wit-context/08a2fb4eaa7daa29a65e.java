/* @test    @summary Test deleteCharAt method for StringBuffer with boundary and invalid index cases    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testDeleteCharAtValidIndex();
        testDeleteCharAtNegativeIndex();
        testDeleteCharAtIndexEqualLength();
        testDeleteCharAtIndexGreaterThanLength();
        testDeleteCharAtIndexLessThanZero();
    }

    static void testDeleteCharAtValidIndex() {
        StringBuffer sb = new StringBuffer("hello");
        sb.deleteCharAt(1);
        if (!"hllo".equals(sb.toString())) {
            throw new AssertionError("Expected 'hllo', but got: " + sb.toString());
        }
    }

    static void testDeleteCharAtNegativeIndex() {
        StringBuffer sb = new StringBuffer("world");
        try {
            sb.deleteCharAt(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testDeleteCharAtIndexEqualLength() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.deleteCharAt(4);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index equal to length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testDeleteCharAtIndexGreaterThanLength() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.deleteCharAt(10);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index greater than length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testDeleteCharAtIndexLessThanZero() {
        StringBuffer sb = new StringBuffer("xyz");
        try {
            sb.deleteCharAt(-5);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
