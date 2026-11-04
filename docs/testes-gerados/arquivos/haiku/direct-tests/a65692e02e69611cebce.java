/*
 * @test
 * @summary Verifies observable behavior of the BufferedInputStream(InputStream) constructor, which delegates to the default buffer size.
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadAcrossDefaultBuffer();
        testMarkReset();
        testMarkSupported();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReadAcrossDefaultBuffer() throws IOException {
        byte[] data = new byte[20000];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i * 31);
        }
        try (BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data))) {
            byte[] out = new byte[data.length];
            int off = 0;
            int r;
            while (off < out.length && (r = in.read(out, off, out.length - off)) != -1) {
                off += r;
            }
            check(off == data.length, "expected to read all bytes, got " + off);
            for (int i = 0; i < data.length; i++) {
                check(out[i] == data[i], "byte mismatch at index " + i);
            }
            check(in.read() == -1, "expected end of stream");
        }
    }

    static void testMarkReset() throws IOException {
        byte[] data = new byte[] {'a', 'b', 'c', 'd', 'e', 'f'};
        try (BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data))) {
            in.mark(10);
            check(in.read() == 'a', "first read after mark");
            check(in.read() == 'b', "second read after mark");
            check(in.read() == 'c', "third read after mark");
            in.reset();
            check(in.read() == 'a', "read after reset should restart at marked position");
        }
    }

    static void testMarkSupported() throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(new byte[0]))) {
            check(in.markSupported(), "markSupported should be true");
        }
    }
}
