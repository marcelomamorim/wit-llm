/*
 * @test
 * @summary Checks public write(ByteBuffer, long) behavior of the AsynchronousFileChannelImpl write path via AsynchronousFileChannel.open
 * @run main AsynchronousFileChannelImplWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsynchronousFileChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        Path tmp = Files.createTempFile("witup", ".dat");
        try {
            try (AsynchronousFileChannel ch =
                     AsynchronousFileChannel.open(tmp, StandardOpenOption.WRITE)) {

                Future<Integer> f1 = ch.write(
                        ByteBuffer.wrap("hello".getBytes(StandardCharsets.US_ASCII)), 0L);
                check(f1 != null, "write must return a non-null Future");
                int n1 = f1.get();
                check(n1 == 5, "expected 5 bytes written, got " + n1);

                Future<Integer> f2 = ch.write(
                        ByteBuffer.wrap("XY".getBytes(StandardCharsets.US_ASCII)), 10L);
                int n2 = f2.get();
                check(n2 == 2, "expected 2 bytes written, got " + n2);

                Future<Integer> f3 = ch.write(ByteBuffer.allocate(0), 0L);
                int n3 = f3.get();
                check(n3 == 0, "expected 0 bytes for empty buffer, got " + n3);
            }

            byte[] data = Files.readAllBytes(tmp);
            check(data.length == 12, "expected file length 12, got " + data.length);
            check(new String(data, 0, 5, StandardCharsets.US_ASCII).equals("hello"),
                    "prefix content mismatch");
            for (int i = 5; i < 10; i++) {
                check(data[i] == 0, "gap byte at " + i + " must be zero");
            }
            check(new String(data, 10, 2, StandardCharsets.US_ASCII).equals("XY"),
                    "positional write content mismatch");
        } finally {
            Files.deleteIfExists(tmp);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
