/*
 * @test
 * @summary Checks InflaterOutputStream constructor argument validation and a basic inflate round trip
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullOutput();
        testNullInflater();
        testNonPositiveBufferLength();
        testValidRoundTrip();
    }

    static void testNullOutput() {
        try {
            new InflaterOutputStream(null, new Inflater(), 16);
            throw new RuntimeException("Expected NullPointerException for null output");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullInflater() {
        try {
            new InflaterOutputStream(new ByteArrayOutputStream(), null, 16);
            throw new RuntimeException("Expected NullPointerException for null inflater");
        } catch (NullPointerException expected) {
        }
    }

    static void testNonPositiveBufferLength() {
        int[] lengths = {0, -1};
        for (int len : lengths) {
            try {
                new InflaterOutputStream(new ByteArrayOutputStream(), new Inflater(), len);
                throw new RuntimeException("Expected IllegalArgumentException for bufLen " + len);
            } catch (IllegalArgumentException expected) {
            }
        }
    }

    static void testValidRoundTrip() throws Exception {
        byte[] original = "hello world".getBytes("UTF-8");

        Deflater deflater = new Deflater();
        deflater.setInput(original);
        deflater.finish();
        byte[] compressed = new byte[1024];
        int n = 0;
        while (!deflater.finished()) {
            n += deflater.deflate(compressed, n, compressed.length - n);
        }
        deflater.end();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(bos, new Inflater(), 16);
        ios.write(Arrays.copyOf(compressed, n));
        ios.close();

        byte[] result = bos.toByteArray();
        if (!Arrays.equals(original, result)) {
            throw new RuntimeException("Round trip mismatch");
        }
    }
}
