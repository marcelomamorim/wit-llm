/*
 * @test
 * @summary WITUP-derived tests for the ZipFile(File) constructor
 * @run main ZipFileWitupTest
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipFileWitupTest {

    public static void main(String[] args) throws Exception {
        testNullFileThrowsNPE();
        testMissingFileThrowsIOException();
        testNonZipFileThrowsZipException();
        testValidZipFileOpens();
        System.out.println("PASSED");
    }

    static void testNullFileThrowsNPE() {
        try {
            new ZipFile((File) null);
        } catch (NullPointerException expected) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException for null file", e);
        }
        throw new RuntimeException("expected NullPointerException for null file");
    }

    static void testMissingFileThrowsIOException() {
        File f = new File("witup-nonexistent-dir", "missing-witup.zip");
        try {
            new ZipFile(f);
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("expected IOException for missing file");
    }

    static void testNonZipFileThrowsZipException() throws IOException {
        File f = File.createTempFile("witup-nonzip", ".zip");
        try {
            try (OutputStream os = new FileOutputStream(f)) {
                os.write("not a zip".getBytes("US-ASCII"));
            }
            try {
                new ZipFile(f);
            } catch (ZipException expected) {
                return;
            }
            throw new RuntimeException("expected ZipException for non-zip file");
        } finally {
            f.delete();
        }
    }

    static void testValidZipFileOpens() throws IOException {
        File f = File.createTempFile("witup-valid", ".zip");
        try {
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(f))) {
                zos.putNextEntry(new ZipEntry("a.txt"));
                zos.write("hello".getBytes("US-ASCII"));
                zos.closeEntry();
            }
            try (ZipFile zf = new ZipFile(f)) {
                if (zf.size() != 1) {
                    throw new RuntimeException("expected 1 entry, got " + zf.size());
                }
                if (zf.getEntry("a.txt") == null) {
                    throw new RuntimeException("entry a.txt not found");
                }
            }
        } finally {
            f.delete();
        }
    }
}
