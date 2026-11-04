/* @test    @summary Test constructor ZipOutputStream(OutputStream, Charset) for correct behavior and null handling    @run main ZipOutputStreamWitupTest */

import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipException;

public class ZipOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidOutputStreamAndCharset();
        testConstructorWithNullCharset();
    }

    static void testConstructorWithValidOutputStreamAndCharset() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            Charset charset = StandardCharsets.UTF_8;
            ZipOutputStream zos = new ZipOutputStream(baos, charset);
            if (zos == null) {
                throw new AssertionError("ZipOutputStream instance is null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testConstructorWithNullCharset() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ZipOutputStream zos = new ZipOutputStream(baos, null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
