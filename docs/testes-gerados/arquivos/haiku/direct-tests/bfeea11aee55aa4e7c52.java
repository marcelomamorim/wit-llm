/*
 * @test
 * @summary Verifies ZipInputStream.available() before entry reads, after reading an entry to EOF, and after close.
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] zip = buildZip();

        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zip))) {
            check(zis.available() == 1, "available before first entry should be 1");

            ZipEntry e = zis.getNextEntry();
            check(e != null && "a.txt".equals(e.getName()), "first entry name mismatch");
            check(zis.available() == 1, "available inside entry should be 1");

            byte[] buf = new byte[64];
            while (zis.read(buf, 0, buf.length) != -1) {
                // drain the entry until EOF
            }
            check(zis.available() == 0, "available after entry EOF should be 0");
        }

        ZipInputStream closed = new ZipInputStream(new ByteArrayInputStream(zip));
        closed.close();
        boolean thrown = false;
        try {
            closed.available();
        } catch (IOException expected) {
            thrown = true;
        }
        check(thrown, "available on closed stream should throw IOException");
    }

    private static byte[] buildZip() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(bos)) {
            zos.putNextEntry(new ZipEntry("a.txt"));
            zos.write("hello zip".getBytes("UTF-8"));
            zos.closeEntry();
        }
        return bos.toByteArray();
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
