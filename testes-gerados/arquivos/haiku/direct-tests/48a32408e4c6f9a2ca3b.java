/*
 * @test
 * @summary Verify ZipInputStream.skip returns exact skipped counts, EOF behavior, negative length rejection and closed stream rejection
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipInputStreamWitupTest {

    static byte[] buildZip() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(bos)) {
            zos.putNextEntry(new ZipEntry("a.txt"));
            byte[] data = new byte[1000];
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) i;
            }
            zos.write(data);
            zos.closeEntry();
        }
        return bos.toByteArray();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws IOException {
        byte[] zip = buildZip();

        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zip))) {
            check(zis.getNextEntry() != null, "expected an entry");
            check(zis.skip(0) == 0, "skip(0) should return 0");
            check(zis.skip(300) == 300, "skip(300) should return 300");
            check(zis.skip(10000) == 700, "skip beyond entry should return remaining 700");
            check(zis.skip(5) == 0, "skip after entry EOF should return 0");
            check(zis.read() == -1, "read after entry EOF should return -1");
        }

        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zip))) {
            check(zis.getNextEntry() != null, "expected an entry");
            try {
                zis.skip(-1);
                check(false, "negative skip should throw IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                // expected
            }
        }

        ZipInputStream closed = new ZipInputStream(new ByteArrayInputStream(zip));
        closed.close();
        try {
            closed.skip(1);
            check(false, "skip on closed stream should throw IOException");
        } catch (IOException e) {
            // expected
        }
    }
}
