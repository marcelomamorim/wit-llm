/*
 * @test
 * @summary Test ZipInputStream constructor with InputStream and Charset
 * @run main ZipInputStreamWitupTest
 */

import java.util.zip.ZipInputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ZipInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidConstructor();
        testNullInputStream();
        testNullCharset();
    }

    private static void testValidConstructor() throws Exception {
        byte[] emptyZip = new byte[0];
        InputStream in = new ByteArrayInputStream(emptyZip);
        Charset charset = StandardCharsets.UTF_8;
        ZipInputStream zis = new ZipInputStream(in, charset);
        if (zis == null) {
            throw new RuntimeException("ZipInputStream constructor returned null");
        }
        zis.close();
    }

    private static void testNullInputStream() {
        try {
            new ZipInputStream(null, StandardCharsets.UTF_8);
            throw new RuntimeException("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            if (!"in is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for NullPointerException: " + e.getMessage());
            }
        }
    }

    private static void testNullCharset() {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        try {
            new ZipInputStream(in, null);
            throw new RuntimeException("Expected NullPointerException for null Charset");
        } catch (NullPointerException e) {
            if (!"charset is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for NullPointerException: " + e.getMessage());
            }
        }
    }
}
