/*
 * @test
 * @summary Test constructor InflaterInputStream(InputStream in, Inflater inf, int size) for exceptions and normal behavior
 * @run main InflaterInputStreamWitupTest
 */

import java.util.zip.InflaterInputStream;
import java.util.zip.Inflater;
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;

public class InflaterInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullInputStream();
        testNullInflater();
        testZeroBufferSize();
        testNegativeBufferSize();
        testValidConstruction();
    }

    private static void testNullInputStream() {
        Inflater inf = new Inflater();
        try {
            new InflaterInputStream(null, inf, 10);
            throw new RuntimeException("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullInflater() {
        InputStream in = new ByteArrayInputStream(new byte[10]);
        try {
            new InflaterInputStream(in, null, 10);
            throw new RuntimeException("Expected NullPointerException for null Inflater");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testZeroBufferSize() {
        InputStream in = new ByteArrayInputStream(new byte[10]);
        Inflater inf = new Inflater();
        try {
            new InflaterInputStream(in, inf, 0);
            throw new RuntimeException("Expected IllegalArgumentException for zero buffer size");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNegativeBufferSize() {
        InputStream in = new ByteArrayInputStream(new byte[10]);
        Inflater inf = new Inflater();
        try {
            new InflaterInputStream(in, inf, -1);
            throw new RuntimeException("Expected IllegalArgumentException for negative buffer size");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testValidConstruction() throws IOException {
        InputStream in = new ByteArrayInputStream(new byte[10]);
        Inflater inf = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(in, inf, 10);
        if (iis == null) {
            throw new RuntimeException("InflaterInputStream constructor returned null");
        }
        // test that read() returns -1 on empty input (no compressed data)
        int r = iis.read();
        if (r != -1) {
            throw new RuntimeException("Expected read() to return -1 on empty input, got " + r);
        }
        iis.close();
    }
}
