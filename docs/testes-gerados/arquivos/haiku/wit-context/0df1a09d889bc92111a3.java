/*
 * @test
 * @summary Checks flush() of InflaterOutputStream: IOException after close, normal flush, and decompressed output.
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testFlushAfterCloseThrowsIOException();
        testFlushOnEmptyOpenStreamDoesNotThrow();
        testFlushWritesDecompressedData();
        testRepeatedFlushIsIdempotent();
    }

    private static void testFlushAfterCloseThrowsIOException() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(out);
        ios.close();
        boolean thrown = false;
        try {
            ios.flush();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException from flush() after close()");
        }
    }

    private static void testFlushOnEmptyOpenStreamDoesNotThrow() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(out);
        ios.flush();
        if (out.size() != 0) {
            throw new RuntimeException("Expected no output, got " + out.size() + " bytes");
        }
        ios.close();
    }

    private static void testFlushWritesDecompressedData() throws Exception {
        byte[] data = "WITUP flush test data 0123456789 WITUP flush test data"
                .getBytes("US-ASCII");
        byte[] compressed = deflate(data);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(out);
        ios.write(compressed);
        ios.flush();
        byte[] result = out.toByteArray();
        if (!Arrays.equals(data, result)) {
            throw new RuntimeException("Decompressed output mismatch after flush()");
        }
        ios.close();
    }

    private static void testRepeatedFlushIsIdempotent() throws Exception {
        byte[] data = "repeated flush".getBytes("US-ASCII");
        byte[] compressed = deflate(data);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(out);
        ios.write(compressed);
        ios.flush();
        ios.flush();
        if (!Arrays.equals(data, out.toByteArray())) {
            throw new RuntimeException("Output changed after repeated flush()");
        }
        ios.close();
    }

    private static byte[] deflate(byte[] input) {
        Deflater def = new Deflater();
        def.setInput(input);
        def.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] tmp = new byte[256];
        while (!def.finished()) {
            int n = def.deflate(tmp);
            bos.write(tmp, 0, n);
        }
        def.end();
        return bos.toByteArray();
    }
}
