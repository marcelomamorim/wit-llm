/*
 * @test
 * @summary Verifies ZipInputStream.available() on open, at entry EOF, and after close
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testAvailableOnFreshStream();
        testAvailableAfterEntryEOF();
        testAvailableAfterCloseThrowsIOException();
    }

    static byte[] buildZip() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(bos)) {
            zos.putNextEntry(new ZipEntry("a.txt"));
            zos.write("hello".getBytes(StandardCharsets.UTF_8));
            zos.closeEntry();
        }
        return bos.toByteArray();
    }

    static void testAvailableOnFreshStream() throws IOException {
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(buildZip()));
        try {
            int avail = zis.available();
            if (avail != 1) {
                throw new RuntimeException("Expected available()=1 on fresh stream, got " + avail);
            }
        } finally {
            zis.close();
        }
    }

    static void testAvailableAfterEntryEOF() throws IOException {
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(buildZip()));
        try {
            ZipEntry entry = zis.getNextEntry();
            if (entry == null) {
                throw new RuntimeException("Expected a ZIP entry");
            }
            byte[] buf = new byte[64];
            while (zis.read(buf, 0, buf.length) != -1) {
                // drain entry data until EOF for this entry
            }
            int avail = zis.available();
            if (avail != 0) {
                throw new RuntimeException("Expected available()=0 at entry EOF, got " + avail);
            }
        } finally {
            zis.close();
        }
    }

    static void testAvailableAfterCloseThrowsIOException() throws IOException {
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(buildZip()));
        zis.close();
        try {
            zis.available();
            throw new RuntimeException("Expected IOException from available() after close");
        } catch (IOException e) {
            if (!"Stream closed".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
        }
    }
}
