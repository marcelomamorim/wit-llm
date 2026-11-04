/*
 * @test
 * @summary Tests the public InflaterInputStream(InputStream, Inflater, int) constructor: argument validation and observable decompression behavior
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] original = "hello inflater stream hello inflater stream".getBytes("UTF-8");
        byte[] compressed = compress(original);

        testRoundTripLargeBuffer(compressed, original);
        testRoundTripSmallBuffer(compressed, original);
        testRoundTripDefaultConstructor(compressed, original);
        testNullInputStreamThrowsNPE();
        testNullInflaterThrowsNPE();
        testZeroSizeThrowsIAE();
        testNegativeSizeThrowsIAE();
        testSizeOneAccepted(compressed, original);
    }

    static void testRoundTripLargeBuffer(byte[] compressed, byte[] original) throws IOException {
        InflaterInputStream iis = new InflaterInputStream(
                new ByteArrayInputStream(compressed), new Inflater(), 512);
        byte[] result = readAll(iis);
        iis.close();
        check(java.util.Arrays.equals(original, result), "round trip with size 512 failed");
    }

    static void testRoundTripSmallBuffer(byte[] compressed, byte[] original) throws IOException {
        InflaterInputStream iis = new InflaterInputStream(
                new ByteArrayInputStream(compressed), new Inflater(), 3);
        byte[] result = readAll(iis);
        iis.close();
        check(java.util.Arrays.equals(original, result), "round trip with size 3 failed");
    }

    static void testRoundTripDefaultConstructor(byte[] compressed, byte[] original) throws IOException {
        InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(compressed));
        byte[] result = readAll(iis);
        iis.close();
        check(java.util.Arrays.equals(original, result), "round trip with default constructor failed");
    }

    static void testSizeOneAccepted(byte[] compressed, byte[] original) throws IOException {
        InflaterInputStream iis = new InflaterInputStream(
                new ByteArrayInputStream(compressed), new Inflater(), 1);
        byte[] result = readAll(iis);
        iis.close();
        check(java.util.Arrays.equals(original, result), "round trip with size 1 failed");
    }

    static void testNullInputStreamThrowsNPE() {
        try {
            new InflaterInputStream(null, new Inflater(), 16);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null InputStream");
    }

    static void testNullInflaterThrowsNPE() {
        try {
            new InflaterInputStream(new ByteArrayInputStream(new byte[0]), null, 16);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null Inflater");
    }

    static void testZeroSizeThrowsIAE() {
        try {
            new InflaterInputStream(new ByteArrayInputStream(new byte[0]), new Inflater(), 0);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for size 0");
    }

    static void testNegativeSizeThrowsIAE() {
        try {
            new InflaterInputStream(new ByteArrayInputStream(new byte[0]), new Inflater(), -1);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for size -1");
    }

    static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[7];
        int n;
        while ((n = in.read(b, 0, b.length)) != -1) {
            bos.write(b, 0, n);
        }
        return bos.toByteArray();
    }

    static byte[] compress(byte[] data) {
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

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
