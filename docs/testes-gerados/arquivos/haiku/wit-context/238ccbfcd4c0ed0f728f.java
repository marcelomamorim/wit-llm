/*
 * @test
 * @summary ZipOutputStream(OutputStream, Charset) rejects a null charset and accepts UTF-8
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testValidCharsetWritesLocalHeader();
    }

    static void testNullCharsetThrowsNPE() {
        boolean thrown = false;
        try {
            new ZipOutputStream(new ByteArrayOutputStream(), null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null charset");
        }
    }

    static void testValidCharsetWritesLocalHeader() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(bos, StandardCharsets.UTF_8)) {
            zos.putNextEntry(new ZipEntry("a.txt"));
            zos.write(new byte[] {'x'});
            zos.closeEntry();
        }
        byte[] b = bos.toByteArray();
        if (b.length < 4 || b[0] != 'P' || b[1] != 'K' || b[2] != 3 || b[3] != 4) {
            throw new RuntimeException("missing ZIP local file header signature");
        }
    }
}
