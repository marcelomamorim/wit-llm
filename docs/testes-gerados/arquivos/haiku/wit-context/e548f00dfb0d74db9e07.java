/*
 * @test
 * @summary Witup-derived checks for DeflaterInputStream.available()
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testOpenStreamAvailableReturnsOne();
        testAvailableAfterEofReturnsZero();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testOpenStreamAvailableReturnsOne() throws IOException {
        byte[] data = "hello world".getBytes(StandardCharsets.US_ASCII);
        try (DeflaterInputStream dis =
                 new DeflaterInputStream(new ByteArrayInputStream(data))) {
            check(dis.available() == 1, "available() on open stream should be 1");
        }
    }

    static void testAvailableAfterEofReturnsZero() throws IOException {
        byte[] data = "hello world".getBytes(StandardCharsets.US_ASCII);
        DeflaterInputStream dis =
            new DeflaterInputStream(new ByteArrayInputStream(data));
        byte[] buf = new byte[64];
        while (dis.read(buf) != -1) {
            // drain the stream until end of file
        }
        check(dis.available() == 0, "available() after EOF should be 0");
        dis.close();
    }
}
