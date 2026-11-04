/*
 * @test
 * @summary Test ZipOutputStream(OutputStream, Charset) constructor behavior
 * @run main ZipOutputStreamWitupTest
 */

import java.util.zip.ZipOutputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ZipOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidCharset();
        testConstructorWithNullCharset();
    }

    private static void testConstructorWithValidCharset() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Charset charset = StandardCharsets.UTF_8;
        ZipOutputStream zos = new ZipOutputStream(baos, charset);
        if (zos == null) {
            throw new RuntimeException("ZipOutputStream constructor returned null");
        }
        zos.close();
    }

    private static void testConstructorWithNullCharset() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            new ZipOutputStream(baos, null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            if (!"charset is null".equals(e.getMessage())) {
                throw new RuntimeException("NullPointerException thrown with unexpected message: " + e.getMessage());
            }
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception type thrown: " + t);
        }
    }
}
