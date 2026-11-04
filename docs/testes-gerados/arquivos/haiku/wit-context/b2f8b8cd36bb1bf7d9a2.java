/*
 * @test
 * @summary Verify that DeflaterInputStream.reset() always throws IOException with the documented message
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testResetOnNewStreamThrows() throws IOException {
        byte[] data = "hello deflater".getBytes("UTF-8");
        DeflaterInputStream dis = new DeflaterInputStream(new ByteArrayInputStream(data));
        try {
            dis.reset();
            throw new RuntimeException("reset() did not throw IOException");
        } catch (IOException e) {
            check("mark/reset not supported".equals(e.getMessage()),
                  "Unexpected message: " + e.getMessage());
        }
    }

    private static void testResetAfterReadingThrows() throws IOException {
        byte[] data = "some data to compress".getBytes("UTF-8");
        DeflaterInputStream dis = new DeflaterInputStream(
                new ByteArrayInputStream(data), new Deflater(), 16);
        int first = dis.read();
        check(first != -1, "Expected at least one compressed byte");
        try {
            dis.reset();
            throw new RuntimeException("reset() did not throw after read");
        } catch (IOException e) {
            check("mark/reset not supported".equals(e.getMessage()),
                  "Unexpected message after read: " + e.getMessage());
        }
    }

    private static void testResetWithExplicitDeflaterThrows() throws IOException {
        DeflaterInputStream dis = new DeflaterInputStream(
                new ByteArrayInputStream(new byte[0]), new Deflater(), 8);
        try {
            dis.reset();
            throw new RuntimeException("reset() did not throw with explicit deflater");
        } catch (IOException e) {
            check("mark/reset not supported".equals(e.getMessage()),
                  "Unexpected message: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        testResetOnNewStreamThrows();
        testResetAfterReadingThrows();
        testResetWithExplicitDeflaterThrows();
    }
}
