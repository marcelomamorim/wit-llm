/*
 * @test
 * @summary Test ByteArrayOutputStream.writeBytes(byte[]) method
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class ByteArrayOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteBytesWithNonEmptyArray();
        testWriteBytesWithEmptyArray();
        testWriteBytesWithNullArray();
    }

    private static void testWriteBytesWithNonEmptyArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] input = {1, 2, 3, 4, 5};
        baos.writeBytes(input);
        byte[] result = baos.toByteArray();
        if (!Arrays.equals(result, input)) {
            throw new AssertionError("writeBytes did not write the expected bytes");
        }
    }

    private static void testWriteBytesWithEmptyArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] input = new byte[0];
        baos.writeBytes(input);
        byte[] result = baos.toByteArray();
        if (result.length != 0) {
            throw new AssertionError("writeBytes with empty array should produce empty output");
        }
    }

    private static void testWriteBytesWithNullArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.writeBytes(null);
            throw new AssertionError("writeBytes(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
