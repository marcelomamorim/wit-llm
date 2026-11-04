/*
 * @test
 * @summary Tests constructor argument validation and round-trip compression behavior of DeflaterInputStream(InputStream, Deflater, int)
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;
import java.util.zip.Inflater;

public class DeflaterInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputThrowsNPE();
        testNullDeflaterThrowsNPE();
        testZeroBufLenThrowsIAE();
        testNegativeBufLenThrowsIAE();
        testRoundTripSmallBuffer();
        testRoundTripBufferLargerThanData();
    }

    static void testNullInputThrowsNPE() {
        try {
            new DeflaterInputStream(null, new Deflater(), 16);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNullDeflaterThrowsNPE() {
        try {
            new DeflaterInputStream(new ByteArrayInputStream(new byte[4]), null, 16);
            throw new RuntimeException("Expected NullPointerException for null deflater");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testZeroBufLenThrowsIAE() {
        try {
            new DeflaterInputStream(new ByteArrayInputStream(new byte[4]), new Deflater(), 0);
            throw new RuntimeException("Expected IllegalArgumentException for bufLen 0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNegativeBufLenThrowsIAE() {
        try {
            new DeflaterInputStream(new ByteArrayInputStream(new byte[4]), new Deflater(), -1);
            throw new RuntimeException("Expected IllegalArgumentException for negative bufLen");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testRoundTripSmallBuffer() throws IOException {
        byte[] data = makeData(1000);
        DeflaterInputStream dis = new DeflaterInputStream(
                new ByteArrayInputStream(data), new Deflater(), 1);
        byte[] compressed = readAll(dis);
        dis.close();
        byte[] restored = inflate(compressed, data.length);
        if (!Arrays.equals(data, restored)) {
            throw new RuntimeException("Round trip mismatch with bufLen 1");
        }
    }

    static void testRoundTripBufferLargerThanData() throws IOException {
        byte[] data = makeData(10);
        DeflaterInputStream dis = new DeflaterInputStream(
                new ByteArrayInputStream(data), new Deflater(), 4096);
        byte[] compressed = readAll(dis);
        dis.close();
        byte[] restored = inflate(compressed, data.length);
        if (!Arrays.equals(data, restored)) {
            throw new RuntimeException("Round trip mismatch with large bufLen");
        }
    }

    static byte[] makeData(int n) {
        byte[] data = new byte[n];
        for (int i = 0; i < n; i++) {
            data[i] = (byte) (i % 7);
        }
        return data;
    }

    static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[64];
        int n;
        while ((n = in.read(b, 0, b.length)) != -1) {
            bos.write(b, 0, n);
        }
        return bos.toByteArray();
    }

    static byte[] inflate(byte[] compressed, int expectedLen) throws IOException {
        Inflater inf = new Inflater();
        inf.setInput(compressed);
        byte[] out = new byte[expectedLen];
        int total = 0;
        try {
            while (!inf.finished() && total < expectedLen) {
                int k;
                try {
                    k = inf.inflate(out, total, expectedLen - total);
                } catch (java.util.zip.DataFormatException e) {
                    throw new IOException("Invalid deflate data", e);
                }
                if (k == 0 && (inf.needsInput() || inf.needsDictionary())) {
                    break;
                }
                total += k;
            }
        } finally {
            inf.end();
        }
        if (total != expectedLen) {
            throw new RuntimeException("Inflated length " + total + " != " + expectedLen);
        }
        return out;
    }
}
