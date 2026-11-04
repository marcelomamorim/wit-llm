/*
 * @test
 * @summary Verifies the two-argument InflaterInputStream constructor delegates to the default buffer size and rejects null arguments
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
        testRoundTripLargerThanDefaultBuffer();
        testNullInputStreamThrowsNPE();
        testNullInflaterThrowsNPE();
    }

    static void testRoundTripLargerThanDefaultBuffer() throws IOException {
        byte[] original = new byte[3000];
        for (int i = 0; i < original.length; i++) {
            original[i] = (byte) (i * 31 + 7);
        }
        byte[] compressed = deflate(original);

        InputStream in = new InflaterInputStream(
                new ByteArrayInputStream(compressed), new Inflater());
        byte[] out = readAll(in);
        in.close();

        if (!java.util.Arrays.equals(original, out)) {
            throw new RuntimeException("Decompressed data does not match original");
        }
    }

    static void testNullInputStreamThrowsNPE() {
        try {
            new InflaterInputStream(null, new Inflater());
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null input stream");
    }

    static void testNullInflaterThrowsNPE() {
        try {
            new InflaterInputStream(new ByteArrayInputStream(new byte[0]), null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null inflater");
    }

    static byte[] deflate(byte[] data) {
        Deflater d = new Deflater();
        d.setInput(data);
        d.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] tmp = new byte[256];
        while (!d.finished()) {
            int n = d.deflate(tmp);
            bos.write(tmp, 0, n);
        }
        d.end();
        return bos.toByteArray();
    }

    static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[128];
        int n;
        while ((n = in.read(buf, 0, buf.length)) != -1) {
            bos.write(buf, 0, n);
        }
        return bos.toByteArray();
    }
}
