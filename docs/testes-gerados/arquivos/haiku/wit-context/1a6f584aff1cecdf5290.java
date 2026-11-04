/*
 * @test
 * @summary Verifies ZipOutputStream.setLevel accepts levels -1..9 and rejects other values with IllegalArgumentException
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamWitupTest {

    private static final byte[] DATA =
        "witup setLevel roundtrip data".getBytes(StandardCharsets.UTF_8);

    public static void main(String[] args) throws Exception {
        int[] validLevels = {-1, 0, 1, 5, 9};
        for (int level : validLevels) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try (ZipOutputStream zos = new ZipOutputStream(bos)) {
                zos.setLevel(level);
                writeEntry(zos, "entry.txt", DATA);
            }
            checkRoundTrip(bos.toByteArray(), "entry.txt", DATA, level);
        }

        int[] invalidLevels = {-2, 10, 42, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int level : invalidLevels) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try (ZipOutputStream zos = new ZipOutputStream(bos)) {
                boolean thrown = false;
                try {
                    zos.setLevel(level);
                } catch (IllegalArgumentException expected) {
                    thrown = true;
                }
                if (!thrown) {
                    throw new RuntimeException("Expected IllegalArgumentException for level " + level);
                }
                writeEntry(zos, "after.txt", DATA);
            }
            checkRoundTrip(bos.toByteArray(), "after.txt", DATA, level);
        }
    }

    private static void writeEntry(ZipOutputStream zos, String name, byte[] data)
            throws IOException {
        zos.putNextEntry(new ZipEntry(name));
        zos.write(data);
        zos.closeEntry();
    }

    private static void checkRoundTrip(byte[] zip, String expectedName,
                                       byte[] expectedData, int level)
            throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zip))) {
            ZipEntry entry = zis.getNextEntry();
            if (entry == null) {
                throw new RuntimeException("No entry found for level " + level);
            }
            if (!expectedName.equals(entry.getName())) {
                throw new RuntimeException("Unexpected entry name for level " + level
                        + ": " + entry.getName());
            }
            ByteArrayOutputStream content = new ByteArrayOutputStream();
            byte[] buf = new byte[256];
            int n;
            while ((n = zis.read(buf)) != -1) {
                content.write(buf, 0, n);
            }
            byte[] actual = content.toByteArray();
            if (actual.length != expectedData.length) {
                throw new RuntimeException("Length mismatch for level " + level);
            }
            for (int i = 0; i < actual.length; i++) {
                if (actual[i] != expectedData[i]) {
                    throw new RuntimeException("Content mismatch for level " + level);
                }
            }
            if (zis.getNextEntry() != null) {
                throw new RuntimeException("Unexpected extra entry for level " + level);
            }
        }
    }
}
