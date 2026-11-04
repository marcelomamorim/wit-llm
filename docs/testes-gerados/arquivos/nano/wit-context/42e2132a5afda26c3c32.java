/* @test    @summary Test insert method of StringBuffer for valid and invalid inputs    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertValid();
        testInsertWithNegativeOffset();
        testInsertWithOffsetGreaterThanLength();
    }

    private static void testInsertValid() {
        StringBuffer sb = new StringBuffer("start");
        char[] chars = {'l', 'e'};
        sb.insert(4, chars);
        if (!"startle".equals(sb.toString())) {
            throw new AssertionError("Expected 'startle', but got: " + sb.toString());
        }
    }

    private static void testInsertWithNegativeOffset() {
        StringBuffer sb = new StringBuffer("test");
        char[] chars = {'x'};
        try {
            sb.insert(-1, chars);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertWithOffsetGreaterThanLength() {
        StringBuffer sb = new StringBuffer("test");
        char[] chars = {'x'};
        try {
            sb.insert(10, chars);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset greater than length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
