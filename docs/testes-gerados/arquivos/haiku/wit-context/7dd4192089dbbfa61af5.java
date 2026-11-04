/*
 * @test
 * @summary Verifies the two-argument InflaterOutputStream constructor rejects null arguments and delegates correctly for valid input
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullOutputThrowsNPE();
        testNullInflaterThrowsNPE();
        testValidArgumentsDecompress();
    }

    static void testNullOutputThrowsNPE() {
        try {
            new InflaterOutputStream(null, new Inflater());
            throw new AssertionError("NullPointerException expected for null out");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullInflaterThrowsNPE() {
        try {
            new InflaterOutputStream(new ByteArrayOutputStream(), null);
            throw new AssertionError("NullPointerException expected for null infl");
        } catch (NullPointerException expected) {
        }
    }

    static void testValidArgumentsDecompress() throws Exception {
        byte[] input = "hello witup".getBytes();
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.finish();
        ByteArrayOutputStream compressed = new ByteArrayOutputStream();
        byte[] tmp = new byte[64];
        while (!deflater.finished()) {
            int k = deflater.deflate(tmp);
            compressed.write(tmp, 0, k);
        }
        deflater.end();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(out, new Inflater());
        ios.write(compressed.toByteArray());
        ios.close();

        if (!Arrays.equals(input, out.toByteArray())) {
            throw new AssertionError("Decompressed output does not match original input");
        }
    }
}
