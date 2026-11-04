/*
 * @test
 * @summary Test ZipOutputStream constructor with OutputStream and Charset
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidCharset();
        testConstructorWithNullCharset();
    }

    private static void testConstructorWithValidCharset() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        Charset charset = StandardCharsets.UTF_8;
        ZipOutputStream zos = new ZipOutputStream(out, charset);
        if (zos == null) {
            throw new AssertionError("Constructor returned null");
        }
        zos.close();
    }

    private static void testConstructorWithNullCharset() {
        OutputStream out = new ByteArrayOutputStream();
        try {
            new ZipOutputStream(out, null);
            throw new AssertionError("Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            // Expected exception
            if (!"charset is null".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
