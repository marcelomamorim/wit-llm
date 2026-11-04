/*
 * @test
 * @summary Test for String.getBytes() method, including null charset scenario.
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testGetBytesWithNullCharset();
        testGetBytesWithDefaultCharset();
    }

    private static void testGetBytesWithNullCharset() {
        String str = "test";
        try {
            str.getBytes(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testGetBytesWithDefaultCharset() {
        String str = "abc";
        byte[] result = str.getBytes();
        if (result == null || result.length != 3) {
            System.err.println("Unexpected result from getBytes()");
            System.exit(1);
        }
        if (result[0] != 'a' || result[1] != 'b' || result[2] != 'c') {
            System.err.println("Byte array content mismatch");
            System.exit(1);
        }
    }
}
