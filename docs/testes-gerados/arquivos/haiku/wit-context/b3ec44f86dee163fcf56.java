/*
 * @test
 * @summary Witup-derived tests for InflaterOutputStream.finish()
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testFinishAfterCloseThrowsIOException();
        testFinishWritesDecompressedData();
    }

    static void testFinishAfterCloseThrowsIOException() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(bos);
        ios.close();
        try {
            ios.finish();
            throw new RuntimeException("Expected IOException from finish() after close()");
        } catch (IOException expected) {
            // expected: ensureOpen() reports the closed stream
        }
    }

    static void testFinishWritesDecompressedData() throws Exception {
        byte[] original = "hello witup".getBytes("US-ASCII");

        Deflater def = new Deflater();
        def.setInput(original);
        def.finish();
        byte[] comp = new byte[256];
        int n = 0;
        while (!def.finished()) {
            n += def.deflate(comp, n, comp.length - n);
        }
        def.end();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(bos);
        ios.write(comp, 0, n);
        ios.finish();
        ios.close();

        byte[] result = bos.toByteArray();
        if (!Arrays.equals(original, result)) {
            throw new RuntimeException("finish() did not flush the decompressed data");
        }
    }
}
