/* @test    @summary Test insert method of StringBuilder for valid and invalid inputs    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertValid();
        testInsertWithNegativeOffset();
        testInsertWithOffsetGreaterThanLength();
    }

    private static void testInsertValid() {
        StringBuilder sb = new StringBuilder("start");
        sb.insert(2, 'X');
        if (!"stXart".equals(sb.toString())) {
            throw new AssertionError("Expected 'stXart', but got: " + sb.toString());
        }
    }

    private static void testInsertWithNegativeOffset() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(-1, 'Y');
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertWithOffsetGreaterThanLength() {
        StringBuilder sb = new StringBuilder("hello");
        try {
            sb.insert(10, 'Z');
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset greater than length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
