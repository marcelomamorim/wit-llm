/*
 * @test
 * @summary Test DeflaterInputStream(InputStream, Deflater) constructor
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;
import java.io.IOException;

public class DeflaterInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testValidConstructor();
        testNullInputStream();
        testNullDeflater();
    }

    private static void testValidConstructor() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        InputStream in = new ByteArrayInputStream(data);
        Deflater def = new Deflater();
        DeflaterInputStream dis = new DeflaterInputStream(in, def);
        // Read some bytes to ensure stream works
        int b = dis.read();
        if (b == -1) {
            throw new RuntimeException("Unexpected end of stream");
        }
        dis.close();
    }

    private static void testNullInputStream() {
        Deflater def = new Deflater();
        try {
            new DeflaterInputStream(null, def);
            throw new RuntimeException("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullDeflater() {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        try {
            new DeflaterInputStream(in, null);
            throw new RuntimeException("Expected NullPointerException for null Deflater");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
