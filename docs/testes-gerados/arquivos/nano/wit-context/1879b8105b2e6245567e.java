/*
 * @test
 * @summary Test for StringBuffer.setCharAt with valid and invalid indices
 * @run main StringBufferWitupTest
 */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testSetCharAtValid();
        testSetCharAtNegativeIndex();
        testSetCharAtIndexEqualToLength();
        testSetCharAtIndexGreaterThanLength();
        testSetCharAtIndexLessThanZero();
    }

    static void testSetCharAtValid() {
        StringBuffer sb = new StringBuffer("hello");
        sb.setCharAt(1, 'a');
        if (!"hallo".equals(sb.toString())) {
            throw new AssertionError("Expected 'hallo', but got: " + sb.toString());
        }
    }

    static void testSetCharAtNegativeIndex() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.setCharAt(-1, 'x');
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSetCharAtIndexEqualToLength() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.setCharAt(3, 'z');
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index equal to length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSetCharAtIndexGreaterThanLength() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.setCharAt(10, 'z');
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index greater than length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSetCharAtIndexLessThanZero() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.setCharAt(-5, 'z');
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index less than zero");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
