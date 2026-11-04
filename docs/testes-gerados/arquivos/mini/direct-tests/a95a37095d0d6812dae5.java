/*
 * @test
 * @summary Test DeflaterInputStream constructor with various inputs
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidConstructor();
        testNullInputStream();
        testNullDeflater();
        testInvalidBufferLength();
    }

    private static void testValidConstructor() throws Exception {
        byte[] data = {1, 2, 3};
        InputStream in = new ByteArrayInputStream(data);
        Deflater defl = new Deflater();
        int bufLen = 10;
        DeflaterInputStream dis = new DeflaterInputStream(in, defl, bufLen);
        if (dis == null) {
            throw new AssertionError("Constructor returned null");
        }
    }

    private static void testNullInputStream() {
        Deflater defl = new Deflater();
        try {
            new DeflaterInputStream(null, defl, 10);
            throw new AssertionError("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            if (!"Null input".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message for NullPointerException: " + e.getMessage());
            }
        }
    }

    private static void testNullDeflater() {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        try {
            new DeflaterInputStream(in, null, 10);
            throw new AssertionError("Expected NullPointerException for null Deflater");
        } catch (NullPointerException e) {
            if (!"Null deflater".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message for NullPointerException: " + e.getMessage());
            }
        }
    }

    private static void testInvalidBufferLength() {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        Deflater defl = new Deflater();
        try {
            new DeflaterInputStream(in, defl, 0);
            throw new AssertionError("Expected IllegalArgumentException for buffer length < 1");
        } catch (IllegalArgumentException e) {
            if (!"Buffer size < 1".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message for IllegalArgumentException: " + e.getMessage());
            }
        }
    }
}
