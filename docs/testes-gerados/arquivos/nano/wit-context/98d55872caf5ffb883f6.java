/*
 * @test
 * @summary Tests for StringBuffer.insert(int, String)
 * @run main StringBufferWitupTest
 */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertWithValidOffset();
        testInsertWithNegativeOffset();
        testInsertWithOffsetGreaterThanLength();
    }

    private static void testInsertWithValidOffset() {
        StringBuffer sb = new StringBuffer("Hello");
        sb.insert(5, " World");
        String result = sb.toString();
        if (!"Hello World".equals(result)) {
            throw new AssertionError("Expected 'Hello World' but got: " + result);
        }
    }

    private static void testInsertWithNegativeOffset() {
        StringBuffer sb = new StringBuffer("Hello");
        try {
            sb.insert(-1, "Test");
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertWithOffsetGreaterThanLength() {
        StringBuffer sb = new StringBuffer("Hello");
        try {
            sb.insert(10, "Test");
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset greater than length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
