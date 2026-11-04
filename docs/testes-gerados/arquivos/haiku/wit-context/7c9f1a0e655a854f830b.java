/*
 * @test
 * @summary Tests the two-argument DeflaterOutputStream constructor: null checks and a compress/inflate round trip
 * @run main DeflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class DeflaterOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullOutThrowsNPE();
        testNullDeflaterThrowsNPE();
        testRoundTripWithDefaultBuffer();
    }

    static void testNullOutThrowsNPE() {
        try {
            new DeflaterOutputStream(null, new Deflater());
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException when out is null");
    }

    static void testNullDeflaterThrowsNPE() {
        try {
            new DeflaterOutputStream(new ByteArrayOutputStream(), null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException when def is null");
    }

    static void testRoundTripWithDefaultBuffer() throws Exception {
        byte[] data = new byte[3000];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i * 31 + 7);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Deflater deflater = new Deflater();
        DeflaterOutputStream dos = new DeflaterOutputStream(bos, deflater);
        dos.write(data);
        dos.finish();
        dos.close();
        deflater.end();

        byte[] compressed = bos.toByteArray();
        Inflater inflater = new Inflater();
        inflater.setInput(compressed);
        byte[] result = new byte[data.length];
        int total = 0;
        while (!inflater.finished() && total < result.length) {
            int n = inflater.inflate(result, total, result.length - total);
            if (n == 0 && (inflater.needsInput() || inflater.needsDictionary())) {
                break;
            }
            total += n;
        }
        inflater.end();

        if (total != data.length) {
            throw new RuntimeException("inflated length mismatch: " + total);
        }
        if (!Arrays.equals(data, result)) {
            throw new RuntimeException("round trip content mismatch");
        }
    }
}
