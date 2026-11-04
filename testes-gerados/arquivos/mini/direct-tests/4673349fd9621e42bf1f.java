/*
 * @test
 * @summary Test InflaterOutputStream constructor with various inputs
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;
import java.io.IOException;

public class InflaterOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testValidConstructor();
        testNullOutputStream();
        testNullInflater();
        testInvalidBufferSize();
    }

    private static void testValidConstructor() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Inflater infl = new Inflater();
        InflaterOutputStream ios = new InflaterOutputStream(baos, infl, 1024);
        if (ios == null) {
            throw new AssertionError("InflaterOutputStream constructor returned null");
        }
        ios.close();
    }

    private static void testNullOutputStream() {
        Inflater infl = new Inflater();
        try {
            new InflaterOutputStream(null, infl, 512);
            throw new AssertionError("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException e) {
            if (!"Null output".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message for NullPointerException: " + e.getMessage());
            }
        }
    }

    private static void testNullInflater() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            new InflaterOutputStream(baos, null, 512);
            throw new AssertionError("Expected NullPointerException for null Inflater");
        } catch (NullPointerException e) {
            if (!"Null inflater".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message for NullPointerException: " + e.getMessage());
            }
        }
    }

    private static void testInvalidBufferSize() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Inflater infl = new Inflater();
        try {
            new InflaterOutputStream(baos, infl, 0);
            throw new AssertionError("Expected IllegalArgumentException for buffer size 0");
        } catch (IllegalArgumentException e) {
            if (!"Buffer size < 1".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message for IllegalArgumentException: " + e.getMessage());
            }
        }
        try {
            new InflaterOutputStream(baos, infl, -1);
            throw new AssertionError("Expected IllegalArgumentException for negative buffer size");
        } catch (IllegalArgumentException e) {
            if (!"Buffer size < 1".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message for IllegalArgumentException: " + e.getMessage());
            }
        }
    }
}
