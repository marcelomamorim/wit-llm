/*
 * @test
 * @summary Behavioral tests for InflaterOutputStream.finish()
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    static byte[] compress(byte[] data) {
        Deflater d = new Deflater();
        d.setInput(data);
        d.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] tmp = new byte[1024];
        while (!d.finished()) {
            int n = d.deflate(tmp);
            bos.write(tmp, 0, n);
        }
        d.end();
        return bos.toByteArray();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testFinishFlushesDecompressedData() throws IOException {
        byte[] data = "hello inflater output stream finish".getBytes("UTF-8");
        byte[] compressed = compress(data);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(out);
        ios.write(compressed);
        ios.finish();
        check(Arrays.equals(out.toByteArray(), data), "finish did not produce full uncompressed output");
    }

    static void testFinishWithExplicitInflaterKeepsInflaterUsable() throws IOException {
        byte[] data = "explicit inflater data".getBytes("UTF-8");
        byte[] compressed = compress(data);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Inflater inf = new Inflater();
        InflaterOutputStream ios = new InflaterOutputStream(out, inf);
        ios.write(compressed);
        ios.finish();
        check(Arrays.equals(out.toByteArray(), data), "unexpected output with explicit inflater");
        check(inf.finished(), "explicit inflater should report finished");
        inf.getRemaining();
        inf.end();
    }

    static void testFinishWithNoInputProducesNoOutput() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(out);
        ios.finish();
        check(out.size() == 0, "finish on empty stream should produce no output");
    }

    static void testFinishAfterCloseThrows() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(out);
        ios.close();
        boolean thrown = false;
        try {
            ios.finish();
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "finish after close should throw IOException");
    }

    public static void main(String[] args) throws Exception {
        testFinishFlushesDecompressedData();
        testFinishWithExplicitInflaterKeepsInflaterUsable();
        testFinishWithNoInputProducesNoOutput();
        testFinishAfterCloseThrows();
    }
}
