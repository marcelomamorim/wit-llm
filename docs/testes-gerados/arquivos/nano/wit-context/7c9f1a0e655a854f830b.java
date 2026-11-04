/* @test    @summary Test for DeflaterOutputStream constructor with OutputStream and Deflater parameters    @run main DeflaterOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class DeflaterOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithNullOutputStream();
        testConstructorWithNullDeflater();
        testConstructorWithValidParameters();
    }

    private static void testConstructorWithNullOutputStream() {
        try {
            new DeflaterOutputStream(null, new Deflater());
            throw new AssertionError("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConstructorWithNullDeflater() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            new DeflaterOutputStream(baos, null);
            throw new AssertionError("Expected NullPointerException for null Deflater");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConstructorWithValidParameters() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Deflater deflater = new Deflater();
        DeflaterOutputStream dos = new DeflaterOutputStream(baos, deflater);
        if (dos == null) {
            throw new AssertionError("Constructor returned null");
        }
        // Additional behavior tests could be added here if needed
        dos.close();
    }
}
