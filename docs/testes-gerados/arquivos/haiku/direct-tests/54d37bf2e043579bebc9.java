/*
 * @test
 * @summary Verifies public behavior of ZipFile.getEntry(String) for existing, missing, case-sensitive and null names
 * @run main ZipFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipFileWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void writeZip(File file) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file))) {
            zos.putNextEntry(new ZipEntry("a.txt"));
            zos.write("hello".getBytes(StandardCharsets.UTF_8));
            zos.closeEntry();

            zos.putNextEntry(new ZipEntry("dir/b.txt"));
            zos.write("world!".getBytes(StandardCharsets.UTF_8));
            zos.closeEntry();
        }
    }

    public static void main(String[] args) throws Exception {
        File file = File.createTempFile("witup", ".zip");
        try {
            writeZip(file);

            try (ZipFile zf = new ZipFile(file)) {
                ZipEntry a = zf.getEntry("a.txt");
                check(a != null, "existing entry a.txt not found");
                check("a.txt".equals(a.getName()), "wrong entry name: " + a.getName());
                check(a.getSize() == 5L, "wrong size for a.txt: " + a.getSize());

                ZipEntry b = zf.getEntry("dir/b.txt");
                check(b != null, "existing nested entry dir/b.txt not found");
                check("dir/b.txt".equals(b.getName()), "wrong nested entry name: " + b.getName());
                check(b.getSize() == 6L, "wrong size for dir/b.txt: " + b.getSize());

                check(zf.getEntry("missing.txt") == null, "missing entry should return null");
                check(zf.getEntry("A.txt") == null, "lookup should be case-sensitive");
                check(zf.getEntry("") == null, "empty name should return null");

                boolean npe = false;
                try {
                    zf.getEntry(null);
                } catch (NullPointerException e) {
                    npe = true;
                }
                check(npe, "null name should throw NullPointerException");
            }
        } finally {
            file.delete();
        }
    }
}
