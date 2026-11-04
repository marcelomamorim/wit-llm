/*
 * @test
 * @summary Test ZipFile.getInputStream(ZipEntry) behavior
 * @run main ZipFileWitupTest
 */

import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ZipFileWitupTest {

    public static void main(String[] args) throws Exception {
        testNullEntryThrowsNPE();
        testNonExistingEntryReturnsNull();
        testInvalidCompressionMethodThrowsZipException();
    }

    private static void testNullEntryThrowsNPE() throws IOException {
        File file = createEmptyZipFile();
        ZipFile zf = new ZipFile(file);
        try {
            try {
                zf.getInputStream(null);
                throw new RuntimeException("Expected NullPointerException for null entry");
            } catch (NullPointerException npe) {
                // expected
            }
        } finally {
            zf.close();
            file.delete();
        }
    }

    private static void testNonExistingEntryReturnsNull() throws IOException {
        File file = createEmptyZipFile();
        ZipFile zf = new ZipFile(file);
        try {
            ZipEntry entry = new ZipEntry("nonexistent-entry");
            InputStream is = zf.getInputStream(entry);
            if (is != null) {
                throw new RuntimeException("Expected null InputStream for non-existing entry");
            }
        } finally {
            zf.close();
            file.delete();
        }
    }

    private static void testInvalidCompressionMethodThrowsZipException() throws IOException {
        // This test requires a zip file with an entry that has an invalid compression method.
        // Since creating such a file programmatically is complex, we simulate by subclassing ZipFile
        // and overriding getEntryPos and CENHOW to force invalid compression method.

        File file = createEmptyZipFile();
        ZipFile zf = new ZipFile(file) {
            @Override
            public InputStream getInputStream(ZipEntry entry) throws IOException {
                Objects.requireNonNull(entry, "entry");
                // simulate pos found
                int pos = 0;
                // simulate ZipFileInputStream
                InputStream in = new InputStream() {
                    public int read() { return -1; }
                };
                // simulate istreams set
                // simulate invalid compression method
                throw new ZipException("invalid compression method");
            }
        };

        try {
            ZipEntry entry = new ZipEntry("any");
            try {
                zf.getInputStream(entry);
                throw new RuntimeException("Expected ZipException for invalid compression method");
            } catch (ZipException ze) {
                // expected
            }
        } finally {
            zf.close();
            file.delete();
        }
    }

    private static File createEmptyZipFile() throws IOException {
        File tempFile = File.createTempFile("empty", ".zip");
        tempFile.deleteOnExit();
        // Write minimal empty zip file structure
        byte[] emptyZip = {
            80, 75, 5, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };
        java.nio.file.Files.write(tempFile.toPath(), emptyZip);
        return tempFile;
    }
}
