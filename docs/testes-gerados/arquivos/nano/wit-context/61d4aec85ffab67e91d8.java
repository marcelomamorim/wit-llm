/* @test    @summary Test for StringBuffer.append(char[] str) method to observe behavior and exceptions    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testAppendCharArrayNormal();
        testAppendCharArrayNull();
        testAppendCharArrayEmpty();
        testAppendCharArrayInvalidRange();
    }

    static void testAppendCharArrayNormal() {
        StringBuffer sb = new StringBuffer("Hello");
        char[] chars = {' ', 'W', 'o', 'r', 'l', 'd'};
        StringBuffer result = sb.append(chars);
        if (!result.toString().equals("Hello World")) {
            throw new AssertionError("Expected 'Hello World', but got: " + result.toString());
        }
    }

    static void testAppendCharArrayNull() {
        StringBuffer sb = new StringBuffer("Test");
        try {
            sb.append((char[]) null);
            throw new AssertionError("Expected NullPointerException for null char[]");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testAppendCharArrayEmpty() {
        StringBuffer sb = new StringBuffer("Empty");
        char[] emptyChars = {};
        StringBuffer result = sb.append(emptyChars);
        if (!result.toString().equals("Empty")) {
            throw new AssertionError("Expected 'Empty', but got: " + result.toString());
        }
    }

    static void testAppendCharArrayInvalidRange() {
        StringBuffer sb = new StringBuffer("Range");
        char[] chars = {'a', 'b', 'c', 'd'};
        try {
            // Using reflection to call the private method checkRange to simulate invalid range
            java.lang.reflect.Method checkRange = java.lang.AbstractStringBuilder.class.getDeclaredMethod("checkRange", int.class, int.class, int.class);
            checkRange.setAccessible(true);
            checkRange.invoke(sb, 0, 10, chars.length); // end > length
            throw new AssertionError("Expected IndexOutOfBoundsException for invalid range");
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (!(e.getCause() instanceof IndexOutOfBoundsException)) {
                throw new AssertionError("Expected IndexOutOfBoundsException, but got: " + e.getCause());
            }
        } catch (Exception e) {
            throw new AssertionError("Reflection error: " + e);
        }
    }
}
