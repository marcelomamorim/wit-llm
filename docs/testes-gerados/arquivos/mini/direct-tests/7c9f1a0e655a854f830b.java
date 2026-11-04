/*
 * @test
 * @summary Test DeflaterOutputStream(OutputStream, Deflater) constructor
 * @run main DeflaterOutputStreamWitupTest
 */

import java.util.zip.DeflaterOutputStream;
import java.util.zip.Deflater;
import java.io.OutputStream;
import java.io.IOException;

public class DeflaterOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        // Test with valid OutputStream and Deflater
        OutputStream dummyOut = new OutputStream() {
            public void write(int b) throws IOException {
                // do nothing
            }
        };
        Deflater def = new Deflater();
        DeflaterOutputStream dos = new DeflaterOutputStream(dummyOut, def);
        if (dos == null) {
            throw new RuntimeException("DeflaterOutputStream constructor returned null");
        }
        dos.close();

        // Test with null OutputStream - expect NullPointerException
        try {
            new DeflaterOutputStream(null, def);
            throw new RuntimeException("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test with null Deflater - expect NullPointerException
        try {
            new DeflaterOutputStream(dummyOut, null);
            throw new RuntimeException("Expected NullPointerException for null Deflater");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
