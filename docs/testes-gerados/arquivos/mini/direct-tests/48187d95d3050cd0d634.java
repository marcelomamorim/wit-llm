/*
 * @test
 * @summary Test ZipOutputStream(OutputStream) constructor basic behavior
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;
import java.nio.charset.StandardCharsets;

public class ZipOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        if (zos == null) {
            throw new AssertionError("ZipOutputStream constructor returned null");
        }
        // The constructor should set the charset to UTF-8 internally
        // but since charset is private, we test basic write and close behavior
        zos.close();
        // After close, writing should throw IOException
        boolean threw = false;
        try {
            zos.write(1);
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IOException after close");
        }
    }
}
