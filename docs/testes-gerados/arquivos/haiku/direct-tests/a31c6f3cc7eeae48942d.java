/*
 * @test
 * @summary Tests the default-inflater constructor InflaterOutputStream(OutputStream)
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {
    static int failures = 0;

    static void check(boolean condition, String msg) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    static byte[] deflate(byte[] data) {
        Deflater d = new Deflater();
        d.setInput(data);
        d.finish();
        ByteArrayOutputStream comp = new ByteArrayOutputStream();
        byte[] tmp = new byte[256];
        while (!d.finished()) {
            int n = d.deflate(tmp);
            comp.write(tmp, 0, n);
        }
        d.end();
        return comp.toByteArray();
    }

    static void testRoundTrip() throws IOException {
        byte[] data = "hello inflater output stream".getBytes("UTF-8");
        byte[] compressed = deflate(data);
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(sink);
        ios.write(compressed);
        ios.close();
        byte[] result = sink.toByteArray();
        check(java.util.Arrays.equals(data, result),
              "default constructor should decompress written data");
    }

    static void testNullOutput() {
        boolean thrown = false;
        try {
            new InflaterOutputStream(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null output should throw NullPointerException");
    }

    static void testWriteAfterClose() throws IOException {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(sink);
        ios.close();
        boolean thrown = false;
        try {
            ios.write(1);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "write after close should throw IOException");
    }

    public static void main(String[] args) throws Exception {
        testRoundTrip();
        testNullOutput();
        testWriteAfterClose();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
