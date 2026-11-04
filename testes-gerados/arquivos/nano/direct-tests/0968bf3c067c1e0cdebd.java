/* @test    @summary Test ByteArrayOutputStream.writeBytes(byte[]) method for correct data writing and exception handling.    @run main ByteArrayOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ByteArrayOutputStreamWitupTest {

    public static void main(String[] args) {
        testWriteBytesNormal();
        testWriteBytesNull();
        testWriteBytesEmpty();
    }

    static void testWriteBytesNormal() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = {1, 2, 3, 4, 5};
        baos.writeBytes(data);
        byte[] result = baos.toByteArray();
        boolean isEqual = java.util.Arrays.equals(data, result);
        if (!isEqual) {
            throw new AssertionError("Failed: Data written does not match data read.");
        }
    }

    static void testWriteBytesNull() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.writeBytes(null);
            throw new AssertionError("Expected NullPointerException not thrown.");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testWriteBytesEmpty() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] empty = {};
        baos.writeBytes(empty);
        byte[] result = baos.toByteArray();
        if (result.length != 0) {
            throw new AssertionError("Failed: Buffer should be empty after writing empty array.");
        }
    }
}
