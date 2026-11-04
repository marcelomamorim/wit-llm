/*
 * @test
 * @summary Black-box tests for the public ZipFile(File) constructor
 * @run main ZipFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipFileWitupTest {

    public static void main(String[] args) throws Exception {
        testOpensValidZipAndReadsEntry();
        testNullFileThrowsNullPointerException();
        testMissingFileThrowsIOException();
        testNonZipFileThrowsZipException();
    }

    static File createZip(String content) throws IOException {
        File f = File.createTempFile("witup", ".zip");
        f.deleteOnExit();
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(f))) {
            zos.putNextEntry(new ZipEntry("a.txt"));
            zos.write(content.getBytes(StandardCharsets.UTF_8));
            zos.closeEntry();
        }
        return f;
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    static void testOpensValidZipAndReadsEntry() throws Exception {
        File f = createZip("hello");
        try (ZipFile zf = new ZipFile(f)) {
            check(zf.size() == 1, "expected 1 entry, got " + zf.size());
            ZipEntry e = zf.getEntry("a.txt");
            check(e != null, "entry a.txt not found");
            try (InputStream in = zf.getInputStream(e)) {
                byte[] buf = in.readAllBytes();
                check("hello".equals(new String(buf, StandardCharsets.UTF_8)),
                      "unexpected entry content");
            }
        } finally {
            f.delete();
        }
    }

    static void testNullFileThrowsNullPointerException() {
        boolean thrown = false;
        try {
            new ZipFile((File) null);
        } catch (NullPointerException e) {
            thrown = true;
        } catch (IOException e) {
            throw new RuntimeException("expected NullPointerException, got IOException", e);
        }
        check(thrown, "expected NullPointerException for null File");
    }

    static void testMissingFileThrowsIOException() throws Exception {
        File missing = File.createTempFile("witup-missing", ".zip");
        missing.delete();
        boolean thrown = false;
        try {
            new ZipFile(missing);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "expected IOException for missing file");
    }

    static void testNonZipFileThrowsZipException() throws Exception {
        File f = File.createTempFile("witup-text", ".zip");
        f.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(f)) {
            out.write("not a zip".getBytes(StandardCharsets.UTF_8));
        }
        boolean zipException = false;
        boolean opened = false;
        try (ZipFile zf = new ZipFile(f)) {
            opened = true;
        } catch (ZipException e) {
            zipException = true;
        } finally {
            f.delete();
        }
        check(!opened, "non-zip file should not open");
        check(zipException, "expected ZipException for non-zip file");
    }
}
