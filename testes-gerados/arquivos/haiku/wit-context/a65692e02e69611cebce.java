/*
 * @test
 * @summary Witup-generated behavioral tests for BufferedInputStream(InputStream) and its observable buffering behavior
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testSingleArgConstructorReadsAllBytes();
        testLargeInputAcrossBufferRefills();
        testMarkSupported();
        testMarkResetReplaysBytes();
        testZeroBufferSizeRejected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static byte[] sequence(int n) {
        byte[] b = new byte[n];
        for (int i = 0; i < n; i++) {
            b[i] = (byte) (i * 31 + 7);
        }
        return b;
    }

    static void testSingleArgConstructorReadsAllBytes() throws IOException {
        byte[] data = sequence(100);
        try (BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data))) {
            byte[] out = new byte[data.length];
            int total = 0;
            int r;
            while (total < out.length && (r = in.read(out, total, out.length - total)) != -1) {
                total += r;
            }
            check(total == data.length, "expected " + data.length + " bytes, got " + total);
            check(Arrays.equals(data, out), "content mismatch");
            check(in.read() == -1, "expected EOF after all bytes");
        }
    }

    static void testLargeInputAcrossBufferRefills() throws IOException {
        byte[] data = sequence(20000);
        try (BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data))) {
            for (int i = 0; i < data.length; i++) {
                int b = in.read();
                check(b == (data[i] & 0xFF), "mismatch at index " + i);
            }
            check(in.read() == -1, "expected EOF after large input");
        }
    }

    static void testMarkSupported() throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(sequence(8)))) {
            check(in.markSupported(), "BufferedInputStream must support mark/reset");
        }
    }

    static void testMarkResetReplaysBytes() throws IOException {
        byte[] data = sequence(50);
        try (BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data))) {
            in.mark(10);
            byte[] first = new byte[5];
            int n = in.read(first, 0, 5);
            check(n == 5, "expected 5 bytes before reset");
            in.reset();
            byte[] second = new byte[5];
            n = in.read(second, 0, 5);
            check(n == 5, "expected 5 bytes after reset");
            check(Arrays.equals(first, second), "reset did not replay marked bytes");
        }
    }

    static void testZeroBufferSizeRejected() throws IOException {
        ByteArrayInputStream src = new ByteArrayInputStream(sequence(4));
        boolean thrown = false;
        try {
            new BufferedInputStream(src, 0);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        check(thrown, "buffer size 0 must be rejected with IllegalArgumentException");
    }
}
