/*
 * @test
 * @summary Test StringBuffer.append(char[]) method for correctness and basic behavior
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testAppendCharArrayBasic();
        testAppendCharArrayEmpty();
        testAppendCharArrayNull();
    }

    private static void testAppendCharArrayBasic() {
        StringBuffer sb = new StringBuffer("Hello");
        char[] chars = {' ', 'W', 'o', 'r', 'l', 'd'};
        StringBuffer result = sb.append(chars);
        if (result != sb) {
            throw new AssertionError("append(char[]) should return this");
        }
        String expected = "Hello World";
        if (!sb.toString().equals(expected)) {
            throw new AssertionError("Expected '" + expected + "' but got '" + sb.toString() + "'");
        }
    }

    private static void testAppendCharArrayEmpty() {
        StringBuffer sb = new StringBuffer("Test");
        char[] empty = new char[0];
        StringBuffer result = sb.append(empty);
        if (result != sb) {
            throw new AssertionError("append(char[]) should return this even for empty array");
        }
        String expected = "Test";
        if (!sb.toString().equals(expected)) {
            throw new AssertionError("Appending empty char array should not change content");
        }
    }

    private static void testAppendCharArrayNull() {
        StringBuffer sb = new StringBuffer("Null");
        try {
            sb.append((char[]) null);
            throw new AssertionError("Expected NullPointerException when appending null char array");
        } catch (NullPointerException npe) {
            // Expected
        }
    }
}
