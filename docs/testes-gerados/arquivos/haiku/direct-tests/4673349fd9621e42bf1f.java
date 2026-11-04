/*
 * @test
 * @summary Verify constructor argument validation and basic inflating behavior of InflaterOutputStream
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullOutputThrowsNPE();
        testNullInflaterThrowsNPE();
        testZeroBufferLengthThrowsIAE();
        testNegativeBufferLengthThrowsIAE();
        testValidConstructionAndInflate();
        testSmallBufferInflate();
        testDefaultInflaterConstructorInflate();
    }

    static void testNullOutputThrowsNPE() {
        try {
            new InflaterOutputStream(null, new Inflater(), 16);
            throw new RuntimeException("Expected NullPointerException for null output");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullInflaterThrowsNPE() {
        try {
            new InflaterOutputStream(new ByteArrayOutputStream(), null, 16);
            throw new RuntimeException("Expected NullPointerException for null inflater");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testZeroBufferLengthThrowsIAE() {
        try {
            new InflaterOutputStream(new ByteArrayOutputStream(), new Inflater(), 0);
            throw new RuntimeException("Expected IllegalArgumentException for bufLen 0");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNegativeBufferLengthThrowsIAE() {
        try {
            new InflaterOutputStream(new ByteArrayOutputStream(), new Inflater(), -1);
            throw new RuntimeException("Expected IllegalArgumentException for bufLen -1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static byte[] deflate(byte[] data) {
        Deflater def = new Deflater();
        def.setInput(data);
        def.finish();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] tmp = new byte[256];
        while (!def.finished()) {
            int n = def.deflate(tmp);
            baos.write(tmp, 0, n);
        }
        def.end();
        return baos.toByteArray();
    }

    static void testValidConstructionAndInflate() throws IOException {
        byte[] original = "hello world, hello world".getBytes();
        byte[] compressed = deflate(original);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InflaterOutputStream ios =
            new InflaterOutputStream(out, new Inflater(), 512);
        ios.write(compressed);
        ios.close();
        if (!Arrays.equals(original, out.toByteArray())) {
            throw new RuntimeException("Inflated output mismatch");
        }
    }

    static void testSmallBufferInflate() throws IOException {
        byte[] original = new byte[1000];
        for (int i = 0; i < original.length; i++) {
            original[i] = (byte) (i % 7);
        }
        byte[] compressed = deflate(original);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InflaterOutputStream ios =
            new InflaterOutputStream(out, new Inflater(), 1);
        ios.write(compressed);
        ios.close();
        if (!Arrays.equals(original, out.toByteArray())) {
            throw new RuntimeException("Inflated output mismatch with bufLen 1");
        }
    }

    static void testDefaultInflaterConstructorInflate() throws IOException {
        byte[] original = "default constructor data".getBytes();
        byte[] compressed = deflate(original);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(out);
        ios.write(compressed);
        ios.close();
        if (!Arrays.equals(original, out.toByteArray())) {
            throw new RuntimeException("Inflated output mismatch with default constructor");
        }
    }
}
