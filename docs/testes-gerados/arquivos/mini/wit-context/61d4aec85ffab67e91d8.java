/*
 * @test
 * @summary Test deterministic behavior of StringBuffer.append(char[])
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testAppendCharArrayNull();
        testAppendCharArrayEmpty();
        testAppendCharArrayNormal();
    }

    private static void testAppendCharArrayNull() {
        StringBuffer sb = new StringBuffer("init");
        // According to current source, append(char[] str) does not throw NullPointerException explicitly
        // but super.append(char[]) is called, which in AbstractStringBuilder throws NPE if null
        // So we test observable behavior: expect NullPointerException
        try {
            sb.append((char[]) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testAppendCharArrayEmpty() {
        StringBuffer sb = new StringBuffer("start");
        char[] empty = new char[0];
        StringBuffer result = sb.append(empty);
        if (result != sb) {
            throw new RuntimeException("append should return this");
        }
        if (!sb.toString().equals("start")) {
            throw new RuntimeException("Appending empty char[] should not change content");
        }
    }

    private static void testAppendCharArrayNormal() {
        StringBuffer sb = new StringBuffer("start");
        char[] chars = new char[] {'l', 'e'};
        StringBuffer result = sb.append(chars);
        if (result != sb) {
            throw new RuntimeException("append should return this");
        }
        if (!sb.toString().equals("startle")) {
            throw new RuntimeException("Appending char[] {'l','e'} should result in 'startle'");
        }
    }
}
