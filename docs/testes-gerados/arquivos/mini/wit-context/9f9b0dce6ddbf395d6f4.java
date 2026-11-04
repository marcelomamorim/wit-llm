/*
 * @test
 * @summary Test ZipInputStream constructor with InputStream and Charset for null arguments
 * @run main ZipInputStreamWitupTest
 */

import java.util.zip.ZipInputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ZipInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullInputStream();
        testNullCharset();
        testValidConstructor();
    }

    private static void testNullInputStream() {
        try {
            new ZipInputStream(null, StandardCharsets.UTF_8);
            throw new RuntimeException("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            if (!"in is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for null InputStream: " + e.getMessage());
            }
        }
    }

    private static void testNullCharset() {
        InputStream dummy = new ByteArrayInputStream(new byte[0]);
        try {
            new ZipInputStream(dummy, null);
            throw new RuntimeException("Expected NullPointerException for null Charset");
        } catch (NullPointerException e) {
            if (!"charset is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for null Charset: " + e.getMessage());
            }
        }
    }

    private static void testValidConstructor() throws Exception {
        InputStream dummy = new ByteArrayInputStream(new byte[0]);
        ZipInputStream zis = new ZipInputStream(dummy, StandardCharsets.UTF_8);
        if (zis == null) {
            throw new RuntimeException("Constructor returned null");
        }
        zis.close();
    }
}
