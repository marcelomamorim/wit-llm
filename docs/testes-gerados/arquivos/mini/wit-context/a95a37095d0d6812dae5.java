/*
 * @test
 * @summary Test DeflaterInputStream constructor with invalid arguments
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullInputStream();
        testNullDeflater();
        testInvalidBufferSize();
        testValidConstruction();
    }

    private static void testNullInputStream() {
        Deflater def = new Deflater();
        try {
            new DeflaterInputStream(null, def, 512);
            throw new RuntimeException("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullDeflater() {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        try {
            new DeflaterInputStream(in, null, 512);
            throw new RuntimeException("Expected NullPointerException for null Deflater");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testInvalidBufferSize() {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        Deflater def = new Deflater();
        try {
            new DeflaterInputStream(in, def, 0);
            throw new RuntimeException("Expected IllegalArgumentException for buffer size < 1");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testValidConstruction() throws Exception {
        byte[] data = {1, 2, 3};
        InputStream in = new ByteArrayInputStream(data);
        Deflater def = new Deflater();
        DeflaterInputStream dis = new DeflaterInputStream(in, def, 512);
        if (dis == null) {
            throw new RuntimeException("DeflaterInputStream constructor returned null");
        }
        dis.close();
    }
}
