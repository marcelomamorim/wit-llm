/*
 * @test
 * @summary DeflaterOutputStream(OutputStream, Deflater, int) delegates to the 4-argument constructor; verifies round trip, size validation and null checks
 * @run main DeflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

public class DeflaterOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] data = new byte[5000];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i * 31 + 7);
        }

        testRoundTrip(data, 512);
        testRoundTrip(data, 1);
        testRoundTrip(new byte[0], 16);

        testIllegalSize(0);
        testIllegalSize(-1);

        testNullOut();
        testNullDeflater();
    }

    static void testRoundTrip(byte[] data, int size) throws IOException, DataFormatException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DeflaterOutputStream dos = new DeflaterOutputStream(bos, new Deflater(), size);
        dos.write(data);
        dos.close();
        byte[] restored = inflate(bos.toByteArray());
        if (!Arrays.equals(data, restored)) {
            throw new RuntimeException("Round trip failed for size " + size);
        }
    }

    static void testIllegalSize(int size) {
        try {
            new DeflaterOutputStream(new ByteArrayOutputStream(), new Deflater(), size);
            throw new RuntimeException("Expected IllegalArgumentException for size " + size);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNullOut() {
        try {
            new DeflaterOutputStream(null, new Deflater(), 512);
            throw new RuntimeException("Expected NullPointerException for null out");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullDeflater() {
        try {
            new DeflaterOutputStream(new ByteArrayOutputStream(), null, 512);
            throw new RuntimeException("Expected NullPointerException for null deflater");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static byte[] inflate(byte[] compressed) throws DataFormatException {
        Inflater inf = new Inflater();
        inf.setInput(compressed);
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] tmp = new byte[1024];
        while (!inf.finished()) {
            int n = inf.inflate(tmp);
            if (n == 0 && (inf.needsInput() || inf.needsDictionary())) {
                inf.end();
                throw new RuntimeException("Compressed data truncated");
            }
            result.write(tmp, 0, n);
        }
        inf.end();
        return result.toByteArray();
    }
}
