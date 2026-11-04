/*
 * @test
 * @summary Test DeflaterOutputStream(OutputStream, Deflater, int) constructor
 * @run main DeflaterOutputStreamWitupTest
 */

import java.util.zip.DeflaterOutputStream;
import java.util.zip.Deflater;
import java.io.OutputStream;
import java.io.IOException;

public class DeflaterOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidBufferSize();
        testZeroBufferSize();
        testNegativeBufferSize();
        testNullOutputStream();
        testNullDeflater();
    }

    private static void testValidBufferSize() throws IOException {
        OutputStream dummyOut = new OutputStream() {
            public void write(int b) throws IOException {}
        };
        Deflater def = new Deflater();
        int size = 512;
        DeflaterOutputStream dos = new DeflaterOutputStream(dummyOut, def, size);
        if (dos == null) {
            throw new AssertionError("Constructor returned null");
        }
        def.end();
    }

    private static void testZeroBufferSize() {
        OutputStream dummyOut = new OutputStream() {
            public void write(int b) throws IOException {}
        };
        Deflater def = new Deflater();
        try {
            new DeflaterOutputStream(dummyOut, def, 0);
            throw new AssertionError("Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            def.end();
        }
    }

    private static void testNegativeBufferSize() {
        OutputStream dummyOut = new OutputStream() {
            public void write(int b) throws IOException {}
        };
        Deflater def = new Deflater();
        try {
            new DeflaterOutputStream(dummyOut, def, -1);
            throw new AssertionError("Expected IllegalArgumentException for size<0");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            def.end();
        }
    }

    private static void testNullOutputStream() {
        Deflater def = new Deflater();
        try {
            new DeflaterOutputStream(null, def, 512);
            throw new AssertionError("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException e) {
            // expected
        } finally {
            def.end();
        }
    }

    private static void testNullDeflater() {
        OutputStream dummyOut = new OutputStream() {
            public void write(int b) throws IOException {}
        };
        try {
            new DeflaterOutputStream(dummyOut, null, 512);
            throw new AssertionError("Expected NullPointerException for null Deflater");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
