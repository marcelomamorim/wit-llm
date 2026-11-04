/*
 * @test
 * @summary Verifies argument validation of the InflaterInputStream(InputStream, Inflater, int) constructor and a basic decompression round trip
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testNullInputStreamThrowsNpe();
        testNullInflaterThrowsNpe();
        testZeroSizeThrowsIae();
        testNegativeSizeThrowsIae();
        testValidArgumentsDecompressData();
    }

    static void testNullInputStreamThrowsNpe() {
        try {
            InputStream in = null;
            new InflaterInputStream(in, new Inflater(), 16);
            throw new RuntimeException("expected NullPointerException for null input");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullInflaterThrowsNpe() {
        try {
            Inflater inf = null;
            new InflaterInputStream(new ByteArrayInputStream(new byte[0]), inf, 16);
            throw new RuntimeException("expected NullPointerException for null inflater");
        } catch (NullPointerException expected) {
        }
    }

    static void testZeroSizeThrowsIae() {
        try {
            new InflaterInputStream(new ByteArrayInputStream(new byte[0]), new Inflater(), 0);
            throw new RuntimeException("expected IllegalArgumentException for size 0");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testNegativeSizeThrowsIae() {
        try {
            new InflaterInputStream(new ByteArrayInputStream(new byte[0]), new Inflater(), -1);
            throw new RuntimeException("expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testValidArgumentsDecompressData() throws IOException {
        byte[] data = "hello witup".getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream compressed = new ByteArrayOutputStream();
        DeflaterOutputStream dos = new DeflaterOutputStream(compressed);
        dos.write(data);
        dos.close();

        InflaterInputStream iis = new InflaterInputStream(
                new ByteArrayInputStream(compressed.toByteArray()), new Inflater(), 4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int b;
        while ((b = iis.read()) != -1) {
            out.write(b);
        }
        iis.close();

        byte[] result = out.toByteArray();
        if (!java.util.Arrays.equals(data, result)) {
            throw new RuntimeException("decompressed data mismatch");
        }
    }
}
