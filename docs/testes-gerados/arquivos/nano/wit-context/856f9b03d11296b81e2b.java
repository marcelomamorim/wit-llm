/*
 * @test
 * @summary Test insert method of StringBuffer for valid and invalid offsets
 * @run main StringBufferWitupTest
 */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertValidOffset();
        testInsertNegativeOffset();
        testInsertOffsetExceedingLength();
    }

    private static void testInsertValidOffset() {
        StringBuffer sb = new StringBuffer("hello");
        sb.insert(5, '!');
        if (!"hello!".equals(sb.toString())) {
            throw new AssertionError("Expected 'hello!', but got: " + sb.toString());
        }
    }

    private static void testInsertNegativeOffset() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.insert(-1, 'a');
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertOffsetExceedingLength() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(4, 'z');
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
