/*
 * @test
 * @summary Test DeflaterOutputStream(OutputStream out, Deflater def, int size) constructor behavior
 * @run main DeflaterOutputStreamWitupTest
 */

import java.util.zip.DeflaterOutputStream;
import java.util.zip.Deflater;
import java.io.OutputStream;
import java.io.IOException;

public class DeflaterOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullOutputStream();
        testNullDeflater();
        testZeroBufferSize();
        testValidConstruction();
    }

    private static void testNullOutputStream() {
        Deflater def = new Deflater();
        try {
            new DeflaterOutputStream(null, def, 512);
            throw new RuntimeException("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullDeflater() {
        OutputStream out = new OutputStream() {
            public void write(int b) throws IOException {
                // do nothing
            }
        };
        try {
            new DeflaterOutputStream(out, null, 512);
            throw new RuntimeException("Expected NullPointerException for null Deflater");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testZeroBufferSize() {
        OutputStream out = new OutputStream() {
            public void write(int b) throws IOException {
                // do nothing
            }
        };
        Deflater def = new Deflater();
        try {
            new DeflaterOutputStream(out, def, 0);
            throw new RuntimeException("Expected IllegalArgumentException for buffer size <= 0");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testValidConstruction() throws IOException {
        OutputStream out = new OutputStream() {
            public void write(int b) throws IOException {
                // do nothing
            }
        };
        Deflater def = new Deflater();
        DeflaterOutputStream dos = new DeflaterOutputStream(out, def, 512);
        if (dos == null) {
            throw new RuntimeException("DeflaterOutputStream constructor returned null");
        }
        dos.close(); // just to check no exception on close
    }
}
