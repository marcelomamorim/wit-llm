/*
 * @test
 * @summary Verifies BufferedInputStream.reset() returns to the marked position, rejects reset without a valid mark, and fails on a closed stream.
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testResetWithoutMarkThrows();
        testResetReturnsToMark();
        testResetWithSmallBufferReturnsToMark();
        testResetAfterCloseThrows();
        testResetAtEndOfStream();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testResetWithoutMarkThrows() throws Exception {
        BufferedInputStream b = new BufferedInputStream(
                new ByteArrayInputStream(new byte[] {1, 2, 3}));
        try {
            b.reset();
            throw new RuntimeException("Expected IOException when resetting without mark");
        } catch (IOException e) {
            check("Resetting to invalid mark".equals(e.getMessage()),
                  "Unexpected message: " + e.getMessage());
        }
    }

    static void testResetReturnsToMark() throws Exception {
        byte[] data = {10, 20, 30, 40, 50};
        BufferedInputStream b = new BufferedInputStream(new ByteArrayInputStream(data));
        b.mark(10);
        check(b.read() == 10, "first read after mark");
        check(b.read() == 20, "second read after mark");
        b.reset();
        check(b.read() == 10, "read after reset should restart at mark");
        check(b.read() == 20, "second read after reset");
        check(b.read() == 30, "third read after reset");
    }

    static void testResetWithSmallBufferReturnsToMark() throws Exception {
        byte[] data = {1, 2, 3, 4, 5, 6};
        BufferedInputStream b = new BufferedInputStream(new ByteArrayInputStream(data), 2);
        b.mark(6);
        for (int i = 1; i <= 5; i++) {
            check(b.read() == i, "read " + i + " before reset");
        }
        b.reset();
        check(b.read() == 1, "reset with small buffer should return to first byte");
    }

    static void testResetAfterCloseThrows() throws Exception {
        BufferedInputStream b = new BufferedInputStream(
                new ByteArrayInputStream(new byte[] {1, 2, 3}));
        b.mark(10);
        b.close();
        try {
            b.reset();
            throw new RuntimeException("Expected IOException on reset after close");
        } catch (IOException e) {
            // expected
        }
    }

    static void testResetAtEndOfStream() throws Exception {
        BufferedInputStream b = new BufferedInputStream(
                new ByteArrayInputStream(new byte[] {7, 8, 9}));
        check(b.read() == 7, "read 7");
        check(b.read() == 8, "read 8");
        check(b.read() == 9, "read 9");
        b.mark(10);
        check(b.read() == -1, "end of stream before reset");
        b.reset();
        check(b.read() == -1, "end of stream after reset to mark at end");
    }
}
