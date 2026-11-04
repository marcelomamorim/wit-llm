/*
 * @test
 * @summary Test DeflaterInputStream(InputStream, Deflater) constructor for null arguments
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullInputStream();
        testNullDeflater();
        testValidConstruction();
    }

    private static void testNullInputStream() {
        Deflater def = new Deflater();
        try {
            new DeflaterInputStream(null, def);
            throw new RuntimeException("Expected NullPointerException for null InputStream");
        } catch (NullPointerException expected) {
            // Expected
        }
    }

    private static void testNullDeflater() {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        try {
            new DeflaterInputStream(in, null);
            throw new RuntimeException("Expected NullPointerException for null Deflater");
        } catch (NullPointerException expected) {
            // Expected
        }
    }

    private static void testValidConstruction() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        Deflater def = new Deflater();
        DeflaterInputStream dis = new DeflaterInputStream(in, def);
        if (dis == null) {
            throw new RuntimeException("DeflaterInputStream constructor returned null");
        }
        dis.close();
    }
}
