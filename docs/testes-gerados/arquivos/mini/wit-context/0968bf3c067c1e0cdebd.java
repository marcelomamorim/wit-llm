/*
 * @test
 * @summary Test ByteArrayOutputStream.writeBytes(byte[]) for normal and boundary cases
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class ByteArrayOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteBytesNormal();
        testWriteBytesEmptyArray();
        testWriteBytesNullArray();
    }

    private static void testWriteBytesNormal() {
        byte[] input = {1, 2, 3, 4, 5};
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.writeBytes(input);
        byte[] result = baos.toByteArray();
        if (!Arrays.equals(result, input)) {
            throw new AssertionError("writeBytes did not write expected bytes");
        }
    }

    private static void testWriteBytesEmptyArray() {
        byte[] input = new byte[0];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.writeBytes(input);
        byte[] result = baos.toByteArray();
        if (result.length != 0) {
            throw new AssertionError("writeBytes with empty array should produce empty output");
        }
    }

    private static void testWriteBytesNullArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.writeBytes(null);
            throw new AssertionError("writeBytes(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
