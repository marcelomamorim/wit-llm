/* @test    @summary Test constructor of ZipInputStream with InputStream only    @run main ZipInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithInputStream();
    }

    public static void testConstructorWithInputStream() {
        byte[] data = {0x50, 0x4b, 0x03, 0x04}; // ZIP file signature bytes
        InputStream in = new ByteArrayInputStream(data);
        ZipInputStream zis = new ZipInputStream(in);
        if (zis == null) {
            throw new AssertionError("ZipInputStream instance should not be null");
        }
        System.out.println("Constructor with InputStream only passed.");
    }
}
