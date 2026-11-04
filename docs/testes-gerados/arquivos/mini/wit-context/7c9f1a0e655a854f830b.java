/*
 * @test
 * @summary Test DeflaterOutputStream(OutputStream out, Deflater def) constructor for null arguments and normal usage
 * @run main DeflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class DeflaterOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullOutputStream();
        testNullDeflater();
        testValidConstructor();
    }

    private static void testNullOutputStream() {
        Deflater def = new Deflater();
        try {
            new DeflaterOutputStream(null, def);
            throw new RuntimeException("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            def.end();
        }
    }

    private static void testNullDeflater() {
        OutputStream out = new ByteArrayOutputStream();
        try {
            new DeflaterOutputStream(out, null);
            throw new RuntimeException("Expected NullPointerException for null Deflater");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testValidConstructor() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        Deflater def = new Deflater();
        DeflaterOutputStream dos = null;
        try {
            dos = new DeflaterOutputStream(out, def);
            if (dos == null) {
                throw new RuntimeException("Constructor returned null DeflaterOutputStream");
            }
            // Write empty array to check no exception thrown
            dos.write(new byte[0]);
            dos.flush();
        } finally {
            if (dos != null) {
                dos.close();
            }
            def.end();
        }
    }
}
