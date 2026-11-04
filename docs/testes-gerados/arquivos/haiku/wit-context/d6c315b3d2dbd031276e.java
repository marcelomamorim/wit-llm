/*
 * @test
 * @summary Checks observable Future-based write behavior of AsynchronousFileChannel implementations (write(ByteBuffer, long) without handler)
 * @run main AsynchronousFileChannelImplWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsynchronousFileChannelImplWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Path file = Files.createTempFile("afci-witup", ".tmp");
        try {
            testWriteAtOffset(file);
            testWriteEmptyBuffer(file);
        } finally {
            Files.deleteIfExists(file);
        }
    }

    static void testWriteAtOffset(Path file) throws Exception {
        byte[] payload = "hello".getBytes("UTF-8");
        try (AsynchronousFileChannel ch = AsynchronousFileChannel.open(file,
                StandardOpenOption.WRITE, StandardOpenOption.READ)) {
            ByteBuffer buf = ByteBuffer.wrap(payload);
            Future<Integer> f = ch.write(buf, 3);
            check(f != null, "write must return a non-null Future");
            int n = f.get();
            check(n == payload.length, "expected " + payload.length + " bytes written, got " + n);
            check(!buf.hasRemaining(), "source buffer should be fully consumed");
        }
        byte[] data = Files.readAllBytes(file);
        check(data.length == 8, "file length expected 8, got " + data.length);
        check(data[0] == 0 && data[1] == 0 && data[2] == 0, "gap before offset must be zero-filled");
        for (int i = 0; i < payload.length; i++) {
            check(data[3 + i] == payload[i], "content mismatch at index " + (3 + i));
        }
    }

    static void testWriteEmptyBuffer(Path file) throws Exception {
        Files.write(file, new byte[0]);
        try (AsynchronousFileChannel ch = AsynchronousFileChannel.open(file,
                StandardOpenOption.WRITE, StandardOpenOption.READ)) {
            ByteBuffer empty = ByteBuffer.allocate(0);
            Future<Integer> f = ch.write(empty, 0);
            int n = f.get();
            check(n == 0, "writing an empty buffer should report 0 bytes, got " + n);
        }
        check(Files.size(file) == 0, "file should remain empty after writing zero bytes");
    }
}
