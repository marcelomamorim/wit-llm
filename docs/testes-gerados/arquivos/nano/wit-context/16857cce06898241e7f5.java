/*
 * @test
 * @summary Tests for java.io.ByteArrayOutputStream.write(int b)
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;

public class ByteArrayOutputStreamWitupTest {

    public static void main(String[] args) {
        testWriteNormal();
        testWriteWithLargeBuffer();
        testWriteWithNegativeByte();
    }

    static void testWriteNormal() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write(65); // ASCII 'A'
            byte[] result = baos.toByteArray();
            if (result.length != 1 || result[0] != 65) {
                throw new AssertionError("Failed: Unexpected content after write(65)");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testWriteNormal", e);
        }
    }

    static void testWriteWithLargeBuffer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        try {
            for (int i = 0; i < 1024; i++) {
                baos.write(i % 256);
            }
            byte[] result = baos.toByteArray();
            if (result.length != 1024) {
                throw new AssertionError("Failed: Buffer size mismatch");
            }
        } catch (OutOfMemoryError e) {
            // Acceptable, as this tests potential OOM
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testWriteWithLargeBuffer", e);
        }
    }

    static void testWriteWithNegativeByte() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write(-1); // Should write as 0xFF
            byte[] result = baos.toByteArray();
            if (result.length != 1 || result[0] != (byte) 0xFF) {
                throw new AssertionError("Failed: Unexpected content after write(-1)");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testWriteWithNegativeByte", e);
        }
    }
}
