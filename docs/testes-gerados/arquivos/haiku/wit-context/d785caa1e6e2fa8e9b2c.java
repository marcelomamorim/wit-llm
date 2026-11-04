/*
 * @test
 * @summary Checks observable behavior of InflaterInputStream.read() for round-trip data, unsigned byte values, closed streams and truncated input
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testRoundTripReadsEveryByteThenEof();
        testReadReturnsUnsignedByteValue();
        testReadAfterCloseThrowsIOException();
        testTruncatedInputThrowsIOException();
    }

    static byte[] deflate(byte[] data) {
        Deflater d = new Deflater();
        d.setInput(data);
        d.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] tmp = new byte[64];
        while (!d.finished()) {
            int n = d.deflate(tmp);
            bos.write(tmp, 0, n);
        }
        d.end();
        return bos.toByteArray();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testRoundTripReadsEveryByteThenEof() throws IOException {
        byte[] original = new byte[] {'w', 'i', 't', 'u', 'p', 0, 1, 2, 3, 65, 66, 67};
        byte[] compressed = deflate(original);
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(compressed));
        for (int i = 0; i < original.length; i++) {
            int b = in.read();
            check(b == (original[i] & 0xFF), "byte mismatch at " + i + ": " + b);
        }
        check(in.read() == -1, "expected -1 at end of compressed data");
        check(in.read() == -1, "expected -1 on repeated read after EOF");
        in.close();
    }

    static void testReadReturnsUnsignedByteValue() throws IOException {
        byte[] original = new byte[] {(byte) 0xFF, (byte) 0x80, 0x00};
        byte[] compressed = deflate(original);
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(compressed));
        check(in.read() == 255, "0xFF must be returned as 255");
        check(in.read() == 128, "0x80 must be returned as 128");
        check(in.read() == 0, "0x00 must be returned as 0");
        check(in.read() == -1, "expected -1 after last byte");
        in.close();
    }

    static void testReadAfterCloseThrowsIOException() throws IOException {
        byte[] compressed = deflate(new byte[] {1, 2, 3});
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(compressed));
        in.close();
        boolean thrown = false;
        try {
            in.read();
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "read() after close() must throw IOException");
    }

    static void testTruncatedInputThrowsIOException() throws IOException {
        byte[] original = new byte[4096];
        for (int i = 0; i < original.length; i++) {
            original[i] = (byte) (i * 31 + 7);
        }
        byte[] compressed = deflate(original);
        byte[] truncated = new byte[compressed.length / 2];
        System.arraycopy(compressed, 0, truncated, 0, truncated.length);
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(truncated));
        boolean thrown = false;
        try {
            for (int i = 0; i <= original.length; i++) {
                int b = in.read();
                check(b != -1, "truncated stream must not report clean EOF");
            }
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "truncated compressed input must raise IOException");
        in.close();
    }
}
