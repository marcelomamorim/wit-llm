/*
 * @test
 * @summary Test InflaterInputStream constructor with various inputs
 * @run main InflaterInputStreamWitupTest
 */

import java.util.zip.InflaterInputStream;
import java.util.zip.Inflater;
import java.io.InputStream;
import java.io.IOException;

public class InflaterInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testNullInputStream();
        testNullInflater();
        testZeroBufferSize();
        testNegativeBufferSize();
        testValidConstructor();
    }

    private static void testNullInputStream() {
        try {
            new InflaterInputStream(null, new Inflater(), 10);
            throw new RuntimeException("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullInflater() {
        try {
            new InflaterInputStream(new InputStream() {
                public int read() { return -1; }
            }, null, 10);
            throw new RuntimeException("Expected NullPointerException for null Inflater");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testZeroBufferSize() {
        try {
            new InflaterInputStream(new InputStream() {
                public int read() { return -1; }
            }, new Inflater(), 0);
            throw new RuntimeException("Expected IllegalArgumentException for zero buffer size");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNegativeBufferSize() {
        try {
            new InflaterInputStream(new InputStream() {
                public int read() { return -1; }
            }, new Inflater(), -1);
            throw new RuntimeException("Expected IllegalArgumentException for negative buffer size");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testValidConstructor() throws IOException {
        Inflater inf = new Inflater();
        InputStream in = new InputStream() {
            public int read() { return -1; }
        };
        InflaterInputStream iis = new InflaterInputStream(in, inf, 10);
        if (iis == null) {
            throw new RuntimeException("InflaterInputStream constructor returned null");
        }
        // test that read() returns -1 on empty input
        int r = iis.read();
        if (r != -1) {
            throw new RuntimeException("Expected -1 from read() on empty input, got " + r);
        }
        iis.close();
    }
}
