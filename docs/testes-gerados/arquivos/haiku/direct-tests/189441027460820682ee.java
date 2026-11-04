/*
 * @test
 * @summary Behavioral tests for Deflater.deflate(byte[], int, int) via public API round trips
 * @run main DeflaterWitupTest
 */

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class DeflaterWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static byte[] inflate(byte[] data, int off, int len, int expectedLength) throws Exception {
        Inflater inf = new Inflater();
        inf.setInput(data, off, len);
        byte[] result = new byte[expectedLength];
        int n = inf.inflate(result);
        inf.end();
        check(n == expectedLength, "unexpected inflated length: " + n);
        return result;
    }

    static void testRoundTrip() throws Exception {
        byte[] input = "blahblahblahblahblah".getBytes(StandardCharsets.UTF_8);
        Deflater d = new Deflater();
        d.setInput(input);
        d.finish();
        byte[] out = new byte[200];
        int n = d.deflate(out, 0, out.length);
        check(n > 0, "deflate should produce output");
        check(d.finished(), "deflater should be finished after full output");
        d.end();
        byte[] res = inflate(out, 0, n, input.length);
        check(Arrays.equals(res, input), "round trip mismatch");
    }

    static void testZeroLengthReturnsZero() {
        byte[] input = "abcdef".getBytes(StandardCharsets.UTF_8);
        Deflater d = new Deflater();
        d.setInput(input);
        byte[] out = new byte[64];
        int n = d.deflate(out, 0, 0);
        check(n == 0, "deflate with len 0 must return 0");
        check(!d.finished(), "deflater must not be finished without finish()");
        d.end();
    }

    static void testOffsetWritesAfterOffset() throws Exception {
        byte[] input = "offset-test-offset-test-offset-test".getBytes(StandardCharsets.UTF_8);
        Deflater d = new Deflater();
        d.setInput(input);
        d.finish();
        byte[] out = new byte[300];
        int off = 5;
        int n = d.deflate(out, off, out.length - off);
        check(n > 0, "deflate with offset should produce output");
        d.end();
        for (int i = 0; i < off; i++) {
            check(out[i] == 0, "bytes before offset must be untouched");
        }
        byte[] res = inflate(out, off, n, input.length);
        check(Arrays.equals(res, input), "offset round trip mismatch");
    }

    static void testSmallOutputChunks() throws Exception {
        byte[] input = "chunked-output-chunked-output-chunked-output".getBytes(StandardCharsets.UTF_8);
        Deflater d = new Deflater();
        d.setInput(input);
        d.finish();
        byte[] out = new byte[1000];
        int pos = 0;
        int guard = 0;
        while (!d.finished()) {
            int n = d.deflate(out, pos, 1);
            pos += n;
            check(++guard < 10000, "deflater made no progress");
        }
        d.end();
        byte[] res = inflate(out, 0, pos, input.length);
        check(Arrays.equals(res, input), "chunked round trip mismatch");
    }

    public static void main(String[] args) throws Exception {
        testRoundTrip();
        testZeroLengthReturnsZero();
        testOffsetWritesAfterOffset();
        testSmallOutputChunks();
    }
}
