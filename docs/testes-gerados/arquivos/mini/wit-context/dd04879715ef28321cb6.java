/*
 * @test
 * @summary Test InflaterInputStream(InputStream in, Inflater inf) constructor for null arguments and normal usage
 * @run main InflaterInputStreamWitupTest
 */

import java.util.zip.InflaterInputStream;
import java.util.zip.Inflater;
import java.io.InputStream;
import java.io.IOException;

public class InflaterInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullInputStream();
        testNullInflater();
        testValidConstructor();
    }

    private static void testNullInputStream() {
        Inflater inf = new Inflater();
        try {
            new InflaterInputStream(null, inf);
            throw new RuntimeException("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullInflater() {
        InputStream in = new InputStream() {
            public int read() throws IOException {
                return -1;
            }
        };
        try {
            new InflaterInputStream(in, null);
            throw new RuntimeException("Expected NullPointerException for null Inflater");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testValidConstructor() throws IOException {
        InputStream in = new InputStream() {
            public int read() throws IOException {
                return -1;
            }
        };
        Inflater inf = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(in, inf);
        // Check that reading returns -1 (EOF) since input stream is empty
        int r = iis.read();
        if (r != -1) {
            throw new RuntimeException("Expected -1 from read() on empty stream, got " + r);
        }
        iis.close();
    }
}
