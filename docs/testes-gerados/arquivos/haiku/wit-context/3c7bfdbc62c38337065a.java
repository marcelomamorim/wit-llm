/*
 * @test
 * @summary Witup-generated behavioral tests for ZipFile(String name) constructor
 * @run main ZipFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipFileWitupTest {

    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testMissingFileThrowsIOException();
        testValidZipFileOpens();
    }

    static void testNullNameThrowsNPE() {
        try {
            new ZipFile((String) null);
        } catch (NullPointerException expected) {
            return;
        } catch (IOException e) {
            throw new AssertionError("Expected NullPointerException, got IOException: " + e);
        }
        throw new AssertionError("Expected NullPointerException for null name");
    }

    static void testMissingFileThrowsIOException() throws Exception {
        File missing = new File("witup-missing-" + System.nanoTime() + ".zip");
        if (missing.exists()) {
            throw new AssertionError("Precondition failed: file exists");
        }
        try {
            new ZipFile(missing.getPath());
        } catch (IOException expected) {
            return;
        }
        throw new AssertionError("Expected IOException for missing file");
    }

    static void testValidZipFileOpens() throws Exception {
        File f = File.createTempFile("witup", ".zip");
        try {
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(f))) {
                zos.putNextEntry(new ZipEntry("a.txt"));
                zos.write("hello".getBytes("UTF-8"));
                zos.closeEntry();
            }
            try (ZipFile zf = new ZipFile(f.getPath())) {
                if (zf.size() != 1) {
                    throw new AssertionError("Expected 1 entry, got " + zf.size());
                }
                ZipEntry e = zf.getEntry("a.txt");
                if (e == null) {
                    throw new AssertionError("Entry a.txt not found");
                }
            }
        } finally {
            f.delete();
        }
    }
}
