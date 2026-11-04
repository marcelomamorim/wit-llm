/* @test    @summary Test for DeflaterOutputStream constructor with OutputStream, Deflater, int parameters    @run main DeflaterOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class DeflaterOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullOutputStream();
        testConstructorWithNullDeflater();
        testConstructorWithZeroSize();
        testConstructorWithNegativeSize();
        testConstructorWithValidParameters();
    }

    static void testConstructorWithNullOutputStream() {
        try {
            new DeflaterOutputStream(null, new Deflater(), 1024);
            System.err.println("Failed: Expected NullPointerException for null OutputStream");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConstructorWithNullDeflater() {
        try {
            OutputStream os = new ByteArrayOutputStream();
            new DeflaterOutputStream(os, null, 1024);
            System.err.println("Failed: Expected NullPointerException for null Deflater");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConstructorWithZeroSize() {
        try {
            OutputStream os = new ByteArrayOutputStream();
            new DeflaterOutputStream(os, new Deflater(), 0);
            System.err.println("Failed: Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testConstructorWithNegativeSize() {
        try {
            OutputStream os = new ByteArrayOutputStream();
            new DeflaterOutputStream(os, new Deflater(), -10);
            System.err.println("Failed: Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testConstructorWithValidParameters() {
        try {
            OutputStream os = new ByteArrayOutputStream();
            Deflater deflater = new Deflater();
            DeflaterOutputStream dos = new DeflaterOutputStream(os, deflater, 1024);
            if (dos == null) {
                System.err.println("Failed: Constructor returned null with valid parameters");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for valid parameters: " + e);
        }
    }
}
