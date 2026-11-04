/* @test    @summary Test insert method with float argument in StringBuffer    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertWithFloat();
        testInsertWithNegativeOffset();
        testInsertWithLargeOffset();
    }

    private static void testInsertWithFloat() {
        StringBuffer sb = new StringBuffer("Hello");
        float value = 3.14f;
        StringBuffer result = sb.insert(5, value);
        String expected = "Hello3.14";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result.toString());
        }
    }

    private static void testInsertWithNegativeOffset() {
        StringBuffer sb = new StringBuffer("Test");
        try {
            sb.insert(-1, 1.0f);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertWithLargeOffset() {
        StringBuffer sb = new StringBuffer("Data");
        int largeOffset = sb.length() + 10;
        try {
            sb.insert(largeOffset, 2.718f);
            throw new AssertionError("Expected IndexOutOfBoundsException for large offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
