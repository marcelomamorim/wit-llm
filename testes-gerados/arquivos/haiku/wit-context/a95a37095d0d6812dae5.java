/*
 * @test
 * @summary Witup-generated tests for the DeflaterInputStream(InputStream, Deflater, int) constructor
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;
import java.util.zip.Inflater;

public class DeflaterInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputThrowsNPE();
        testNullDeflaterThrowsNPE();
        testNonPositiveBufferSizeThrowsIAE();
        testValidArgumentsRoundTrip();
    }

    static void testNullInputThrowsNPE() {
        try {
            new DeflaterInputStream(null, new Deflater(), 16);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullDeflaterThrowsNPE() {
        try {
            new DeflaterInputStream(new ByteArrayInputStream(new byte[0]), null, 16);
            throw new RuntimeException("Expected NullPointerException for null deflater");
        } catch (NullPointerException expected) {
        }
    }

    static void testNonPositiveBufferSizeThrowsIAE() {
        int[] sizes = {0, -1};
        for (int size : sizes) {
            try {
                new DeflaterInputStream(new ByteArrayInputStream(new byte[0]), new Deflater(), size);
                throw new RuntimeException("Expected IllegalArgumentException for bufLen " + size);
            } catch (IllegalArgumentException expected) {
            }
        }
    }

    static void testValidArgumentsRoundTrip() throws Exception {
        byte[] data = "hello hello hello hello".getBytes("UTF-8");
        InputStream in = new DeflaterInputStream(new ByteArrayInputStream(data), new Deflater(), 1);
        ByteArrayOutputStream compressedOut = new ByteArrayOutputStream();
        byte[] chunk = new byte[7];
        int n;
        while ((n = in.read(chunk)) != -1) {
            compressedOut.write(chunk, 0, n);
        }
        in.close();
        byte[] compressed = compressedOut.toByteArray();

        Inflater inf = new Inflater();
        inf.setInput(compressed);
        byte[] out = new byte[data.length];
        int total = 0;
        while (!inf.finished()) {
            int k = inf.inflate(out, total, out.length - total);
            total += k;
            if (k == 0 && (inf.needsInput() || inf.needsDictionary())) {
                break;
            }
        }
        inf.end();

        if (total != data.length) {
            throw new RuntimeException("Unexpected decompressed length: " + total);
        }
        String restored = new String(out, 0, total, "UTF-8");
        if (!restored.equals("hello hello hello hello")) {
            throw new RuntimeException("Round trip mismatch: " + restored);
        }
    }
}
