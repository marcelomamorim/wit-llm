/*
 * @test
 * @summary Test constructor of DeflaterOutputStream with specific parameters
 * @run main DeflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class DeflaterOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithValidParameters();
        testConstructorWithNullOutputStream();
        testConstructorWithNullDeflater();
        testConstructorWithInvalidSize();
    }

    static void testConstructorWithValidParameters() {
        OutputStream os = new ByteArrayOutputStream();
        Deflater def = new Deflater();
        int size = 512;
        DeflaterOutputStream dos = new DeflaterOutputStream(os, def, size);
        if (dos == null) {
            throw new AssertionError("Constructor returned null");
        }
    }

    static void testConstructorWithNullOutputStream() {
        try {
            Deflater def = new Deflater();
            new DeflaterOutputStream(null, def, 512);
            throw new AssertionError("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testConstructorWithNullDeflater() {
        try {
            OutputStream os = new ByteArrayOutputStream();
            new DeflaterOutputStream(os, null, 512);
            throw new AssertionError("Expected NullPointerException for null Deflater");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testConstructorWithInvalidSize() {
        try {
            OutputStream os = new ByteArrayOutputStream();
            Deflater def = new Deflater();
            new DeflaterOutputStream(os, def, 0);
            throw new AssertionError("Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        try {
            OutputStream os = new ByteArrayOutputStream();
            Deflater def = new Deflater();
            new DeflaterOutputStream(os, def, -10);
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
