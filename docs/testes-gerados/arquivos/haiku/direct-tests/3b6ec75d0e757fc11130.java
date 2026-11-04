/*
 * @test
 * @summary Verifies ZipFile(File, int) opens an existing archive in OPEN_READ mode and fails for a missing file
 * @run main ZipFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipFileWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        File zip = File.createTempFile("witup", ".zip");
        zip.deleteOnExit();

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip))) {
            zos.putNextEntry(new ZipEntry("a.txt"));
            zos.write("hello".getBytes(StandardCharsets.UTF_8));
            zos.closeEntry();
        }

        try (ZipFile zf = new ZipFile(zip, ZipFile.OPEN_READ)) {
            check(zf.size() == 1, "expected exactly one entry");

            ZipEntry entry = zf.getEntry("a.txt");
            check(entry != null, "entry a.txt not found");

            Enumeration<? extends ZipEntry> en = zf.entries();
            check(en.hasMoreElements(), "entries() should yield a.txt");
            check(en.nextElement().getName().equals("a.txt"), "unexpected entry name");
            check(!en.hasMoreElements(), "entries() should yield only one element");

            try (InputStream in = zf.getInputStream(entry)) {
                byte[] data = in.readAllBytes();
                check(new String(data, StandardCharsets.UTF_8).equals("hello"),
                      "unexpected entry content");
            }
        }

        File missing = new File(zip.getPath() + ".missing");
        boolean thrown = false;
        try {
            new ZipFile(missing, ZipFile.OPEN_READ).close();
        } catch (IOException expected) {
            thrown = true;
        }
        check(thrown, "opening a missing file should throw IOException");
    }
}
