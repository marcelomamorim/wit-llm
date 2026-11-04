/*
 * @test
 * @summary Test ZipInputStream(InputStream in) constructor for null input and normal usage
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullInputStream();
        testValidInputStream();
    }

    private static void testNullInputStream() {
        try {
            new ZipInputStream(null);
            throw new RuntimeException("Expected NullPointerException for null InputStream");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testValidInputStream() throws Exception {
        byte[] emptyZip = new byte[0];
        InputStream in = new ByteArrayInputStream(emptyZip);
        ZipInputStream zis = new ZipInputStream(in);
        if (zis == null) {
            throw new RuntimeException("ZipInputStream constructor returned null");
        }
        zis.close();
    }
}
