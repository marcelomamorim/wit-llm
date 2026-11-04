/*
 * @test
 * @summary Test ZipInputStream constructor with InputStream
 * @run main ZipInputStreamWitupTest
 */

import java.util.zip.ZipInputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class ZipInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testNullInputStream();
        testValidInputStream();
    }

    private static void testNullInputStream() {
        try {
            new ZipInputStream(null);
            throw new RuntimeException("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testValidInputStream() throws IOException {
        byte[] emptyZip = new byte[0];
        InputStream in = new ByteArrayInputStream(emptyZip);
        ZipInputStream zis = new ZipInputStream(in);
        if (zis == null) {
            throw new RuntimeException("ZipInputStream constructor returned null");
        }
        // Try to read next entry, should return null since stream is empty
        if (zis.getNextEntry() != null) {
            throw new RuntimeException("Expected no entries in empty stream");
        }
        zis.close();
    }
}
