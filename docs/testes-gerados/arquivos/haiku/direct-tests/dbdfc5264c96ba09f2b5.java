/*
 * @test
 * @summary Verifies the DeflaterInputStream(InputStream) constructor: null input rejection and deflate round trip with the default compressor
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.DeflaterInputStream;
import java.util.zip.Inflater;

public class DeflaterInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputThrowsNPE();
        testRoundTripDefaultDeflater();
        testEmptyInputRoundTrip();
        testSingleByteReadPath();
        System.out.println("PASSED");
    }

    static void testNullInputThrowsNPE() {
        try {
            new DeflaterInputStream(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException e) {
            // expected: constructor sanity check on null input
        }
    }

    static void testRoundTripDefaultDeflater() throws Exception {
        byte[] original = new byte[1000];
        for (int i = 0; i < original.length; i++) {
            original[i] = (byte) ('a' + (i % 3));
        }
        byte[] compressed = readAll(new DeflaterInputStream(new ByteArrayInputStream(original)));
        if (compressed.length >= original.length) {
            throw new AssertionError("compressed output not smaller: " + compressed.length);
        }
        byte[] restored = inflate(compressed);
        if (!Arrays.equals(original, restored)) {
            throw new AssertionError("round trip mismatch");
        }
    }

    static void testEmptyInputRoundTrip() throws Exception {
        byte[] compressed = readAll(new DeflaterInputStream(new ByteArrayInputStream(new byte[0])));
        byte[] restored = inflate(compressed);
        if (restored.length != 0) {
            throw new AssertionError("expected empty output but got " + restored.length);
        }
    }

    static void testSingleByteReadPath() throws Exception {
        byte[] original = "witup single byte read".getBytes("US-ASCII");
        InputStream in = new DeflaterInputStream(new ByteArrayInputStream(original));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1) {
            if (b < 0 || b > 255) {
                throw new AssertionError("read() out of range: " + b);
            }
            bos.write(b);
        }
        in.close();
        byte[] restored = inflate(bos.toByteArray());
        if (!Arrays.equals(original, restored)) {
            throw new AssertionError("single byte read mismatch");
        }
    }

    static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] chunk = new byte[64];
        int n;
        while ((n = in.read(chunk)) != -1) {
            bos.write(chunk, 0, n);
        }
        in.close();
        return bos.toByteArray();
    }

    static byte[] inflate(byte[] data) throws Exception {
        Inflater inf = new Inflater();
        inf.setInput(data);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[128];
        while (!inf.finished()) {
            int n = inf.inflate(buf);
            if (n == 0 && (inf.needsInput() || inf.needsDictionary())) {
                inf.end();
                throw new AssertionError("truncated compressed data");
            }
            bos.write(buf, 0, n);
        }
        inf.end();
        return bos.toByteArray();
    }
}
