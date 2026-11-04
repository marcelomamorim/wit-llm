/*
 * @test
 * @summary Verifies observable buffering and flushing behavior of BufferedOutputStream.write(int)
 * @run main BufferedOutputStreamWitupTest
 */
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BufferedOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testBuffersUntilCapacityThenFlushes();
        testFlushOnFullBufferBeforeNextByte();
        testOnlyLowEightBitsAreWritten();
        testInvalidSizeRejected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkBytes(byte[] expected, byte[] actual, String message) {
        check(expected.length == actual.length, message + " length expected "
                + expected.length + " but was " + actual.length);
        for (int i = 0; i < expected.length; i++) {
            check(expected[i] == actual[i], message + " at index " + i);
        }
    }

    static void testBuffersUntilCapacityThenFlushes() throws IOException {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(sink, 4);
        bos.write(1);
        bos.write(2);
        bos.write(3);
        bos.write(4);
        check(sink.size() == 0, "bytes reached sink before buffer was exceeded");
        bos.write(5);
        checkBytes(new byte[] {1, 2, 3, 4}, sink.toByteArray(),
                "buffer not flushed when full");
        bos.flush();
        checkBytes(new byte[] {1, 2, 3, 4, 5}, sink.toByteArray(),
                "final flush output mismatch");
    }

    static void testFlushOnFullBufferBeforeNextByte() throws IOException {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(sink, 2);
        bos.write(10);
        bos.write(20);
        checkBytes(new byte[0], sink.toByteArray(), "premature flush");
        bos.write(30);
        checkBytes(new byte[] {10, 20}, sink.toByteArray(),
                "full buffer not written before new byte");
        bos.write(40);
        bos.flush();
        checkBytes(new byte[] {10, 20, 30, 40}, sink.toByteArray(),
                "sequence order corrupted across flushes");
    }

    static void testOnlyLowEightBitsAreWritten() throws IOException {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(sink);
        bos.write(0x1FF);
        bos.write(0x100);
        bos.write(-1);
        bos.write(0x41);
        bos.flush();
        checkBytes(new byte[] {(byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x41},
                sink.toByteArray(), "write(int) did not keep only low 8 bits");
    }

    static void testInvalidSizeRejected() throws IOException {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        try {
            new BufferedOutputStream(sink, 0);
            throw new RuntimeException("size 0 accepted");
        } catch (IllegalArgumentException expected) {
        }
        try {
            new BufferedOutputStream(sink, -1);
            throw new RuntimeException("negative size accepted");
        } catch (IllegalArgumentException expected) {
        }
    }
}
