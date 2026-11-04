/*
 * @test
 * @summary Verifies BufferedOutputStream(OutputStream, int) argument validation and observable buffering behavior
 * @run main BufferedOutputStreamWitupTest
 */

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BufferedOutputStreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testZeroSizeRejected() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            new BufferedOutputStream(bos, 0);
            throw new RuntimeException("expected IllegalArgumentException for size 0");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNegativeSizeRejected() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            new BufferedOutputStream(bos, -1);
            throw new RuntimeException("expected IllegalArgumentException for size -1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testPositiveSizeAccepted() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BufferedOutputStream out = new BufferedOutputStream(bos, 1);
        out.write(7);
        check(bos.size() == 0, "size-1 buffer should hold the byte until flushed");
        out.flush();
        check(bos.size() == 1, "flush should push one byte downstream");
        check(bos.toByteArray()[0] == 7, "unexpected byte value");
    }

    static void testBufferHoldsUntilFull() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BufferedOutputStream out = new BufferedOutputStream(bos, 2);
        out.write(1);
        out.write(2);
        check(bos.size() == 0, "bytes should be buffered while buffer is not exceeded");
        out.write(3);
        check(bos.size() == 2, "writing a third byte should flush the full buffer");
        out.flush();
        byte[] data = bos.toByteArray();
        check(data.length == 3, "expected 3 bytes after flush");
        check(data[0] == 1 && data[1] == 2 && data[2] == 3, "byte order not preserved");
    }

    public static void main(String[] args) throws IOException {
        testZeroSizeRejected();
        testNegativeSizeRejected();
        testPositiveSizeAccepted();
        testBufferHoldsUntilFull();
    }
}
