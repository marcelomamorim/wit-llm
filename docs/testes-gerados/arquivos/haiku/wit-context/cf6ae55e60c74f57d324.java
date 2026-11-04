/*
 * @test
 * @summary Witup-generated tests for the JarFile(File) constructor
 * @run main JarFileWitupTest
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipException;

public class JarFileWitupTest {

    public static void main(String[] args) throws Exception {
        testNullFileThrowsNPE();
        testMissingFileThrowsIOException();
        testInvalidContentThrowsZipException();
        testValidJarOpens();
    }

    static void testNullFileThrowsNPE() {
        try {
            new JarFile((File) null);
        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("Expected NullPointerException, got IOException", e);
        }
        throw new RuntimeException("Expected NullPointerException for null File");
    }

    static void testMissingFileThrowsIOException() {
        File f = new File("witup-missing-" + System.nanoTime() + ".jar");
        if (f.exists()) {
            throw new RuntimeException("Precondition failed: file exists");
        }
        try {
            new JarFile(f);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for missing file");
    }

    static void testInvalidContentThrowsZipException() throws IOException {
        File f = new File("witup-invalid.jar");
        try (OutputStream out = new FileOutputStream(f)) {
            out.write("not a zip archive".getBytes("US-ASCII"));
        }
        try {
            new JarFile(f);
        } catch (ZipException e) {
            return;
        } finally {
            f.delete();
        }
        throw new RuntimeException("Expected ZipException for invalid content");
    }

    static void testValidJarOpens() throws IOException {
        File f = new File("witup-valid.jar");
        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(f))) {
            jos.putNextEntry(new JarEntry("a.txt"));
            jos.write("hello".getBytes("US-ASCII"));
            jos.closeEntry();
        }
        try (JarFile jf = new JarFile(f)) {
            if (!jf.getName().equals(f.getPath())) {
                throw new RuntimeException("Unexpected name: " + jf.getName());
            }
            if (jf.getEntry("a.txt") == null) {
                throw new RuntimeException("Entry a.txt not found");
            }
        } finally {
            f.delete();
        }
    }
}
