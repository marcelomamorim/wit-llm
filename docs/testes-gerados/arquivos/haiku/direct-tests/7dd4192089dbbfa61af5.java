/*
 * @test
 * @summary Verify InflaterOutputStream(OutputStream, Inflater) inflates data written to it and rejects null arguments
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testRoundTripLarge();
        testRoundTripByteByByte();
        testNullOutput();
        testNullInflater();
    }

    static void testRoundTripLarge() throws Exception {
        byte[] data = new byte[10000];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) ((i * 31) % 251);
        }
        byte[] compressed = deflate(data);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(out, new Inflater());
        ios.write(compressed);
        ios.close();
        check(Arrays.equals(data, out.toByteArray()), "large round trip mismatch");
    }

    static void testRoundTripByteByByte() throws Exception {
        byte[] data = new byte[] {1, 2, 3, 4, 5, 0, 127, -128, -1};
        byte[] compressed = deflate(data);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(out, new Inflater());
        for (int i = 0; i < compressed.length; i++) {
            ios.write(compressed[i]);
        }
        ios.close();
        check(Arrays.equals(data, out.toByteArray()), "byte-by-byte round trip mismatch");
    }

    static void testNullOutput() {
        boolean thrown = false;
        try {
            new InflaterOutputStream(null, new Inflater());
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null output");
    }

    static void testNullInflater() {
        boolean thrown = false;
        try {
            new InflaterOutputStream(new ByteArrayOutputStream(), null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null inflater");
    }

    static byte[] deflate(byte[] input) {
        Deflater d = new Deflater();
        d.setInput(input);
        d.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[65536];
        while (!d.finished()) {
            int n = d.deflate(buf);
            bos.write(buf, 0, n);
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
