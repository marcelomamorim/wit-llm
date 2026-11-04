/*
 * @test
 * @summary Witup-derived checks for DeflaterInputStream(InputStream, Deflater) null guards and round-trip behavior
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;
import java.util.zip.Inflater;

public class DeflaterInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputThrowsNpe();
        testNullDeflaterThrowsNpe();
        testNullInputWithNullDeflaterThrowsNpe();
        testRoundTrip();
    }

    static void testNullInputThrowsNpe() {
        Deflater d = new Deflater();
        try {
            new DeflaterInputStream(null, d);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            d.end();
        }
    }

    static void testNullDeflaterThrowsNpe() {
        InputStream in = new ByteArrayInputStream(new byte[] {1, 2, 3});
        try {
            new DeflaterInputStream(in, null);
            throw new RuntimeException("Expected NullPointerException for null deflater");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullInputWithNullDeflaterThrowsNpe() {
        try {
            new DeflaterInputStream(null, null);
            throw new RuntimeException("Expected NullPointerException for null input and deflater");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRoundTrip() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            sb.append("witup-deflater-").append(i % 7).append(';');
        }
        byte[] data = sb.toString().getBytes("US-ASCII");

        Deflater d = new Deflater();
        byte[] compressed;
        try (InputStream in = new DeflaterInputStream(new ByteArrayInputStream(data), d)) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[64];
            int n;
            while ((n = in.read(buf)) != -1) {
                bos.write(buf, 0, n);
            }
            compressed = bos.toByteArray();
        } finally {
            d.end();
        }

        Inflater inf = new Inflater();
        try {
            inf.setInput(compressed);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] tmp = new byte[128];
            while (!inf.finished()) {
                int k = inf.inflate(tmp);
                if (k == 0 && (inf.needsInput() || inf.needsDictionary())) {
                    break;
                }
                out.write(tmp, 0, k);
            }
            if (!inf.finished()) {
                throw new RuntimeException("Inflater did not finish");
            }
            if (!Arrays.equals(data, out.toByteArray())) {
                throw new RuntimeException("Round-trip data mismatch");
            }
        } finally {
            inf.end();
        }
    }
}
