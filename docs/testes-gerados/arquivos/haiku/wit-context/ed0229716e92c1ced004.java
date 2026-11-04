/*
 * @test
 * @summary WITUP-derived checks for BufferedOutputStream(OutputStream, int) size validation and observable buffering
 * @run main BufferedOutputStreamWitupTest
 */

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BufferedOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testInvalidSizeThrows(0);
        testInvalidSizeThrows(-1);
        testInvalidSizeThrows(Integer.MIN_VALUE);
        testSmallBufferWriteOrder();
        testDefaultConstructorBuffersUntilFlush();
    }

    static void testInvalidSizeThrows(int size) {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        try {
            new BufferedOutputStream(sink, size);
        } catch (IllegalArgumentException e) {
            if (!"Buffer size <= 0".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for size " + size);
    }

    static void testSmallBufferWriteOrder() throws IOException {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(sink, 1);
        bos.write('a');
        if (sink.size() != 0) {
            throw new RuntimeException("byte should remain buffered");
        }
        bos.write('b');
        byte[] afterSecond = sink.toByteArray();
        if (afterSecond.length != 1 || afterSecond[0] != 'a') {
            throw new RuntimeException("expected 'a' flushed before 'b' is buffered");
        }
        bos.flush();
        byte[] afterFlush = sink.toByteArray();
        if (afterFlush.length != 2 || afterFlush[0] != 'a' || afterFlush[1] != 'b') {
            throw new RuntimeException("expected \"ab\" after flush");
        }
    }

    static void testDefaultConstructorBuffersUntilFlush() throws IOException {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(sink);
        bos.write(new byte[] {1, 2, 3, 4, 5});
        if (sink.size() != 0) {
            throw new RuntimeException("data should be buffered with default size");
        }
        bos.flush();
        byte[] out = sink.toByteArray();
        if (out.length != 5) {
            throw new RuntimeException("expected 5 bytes after flush, got " + out.length);
        }
        for (int i = 0; i < 5; i++) {
            if (out[i] != i + 1) {
                throw new RuntimeException("wrong byte at index " + i);
            }
        }
    }
}
