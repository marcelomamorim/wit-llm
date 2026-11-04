/*
 * @test
 * @summary Witup-generated tests for JarFile(String name) constructor
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class JarFileWitupTest {

    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testMissingFileThrowsIOException();
        testValidJarOpensByName();
    }

    static void testNullNameThrowsNPE() {
        try {
            new JarFile((String) null);
        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException for null name", e);
        }
        throw new RuntimeException("expected NullPointerException for null name");
    }

    static void testMissingFileThrowsIOException() {
        File f = new File("witup-missing-" + System.nanoTime() + ".jar");
        if (f.exists()) {
            throw new RuntimeException("precondition: file should not exist");
        }
        try {
            new JarFile(f.getPath());
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException for missing file");
    }

    static void testValidJarOpensByName() throws IOException {
        File f = File.createTempFile("witup", ".jar");
        try {
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(f))) {
                zos.putNextEntry(new ZipEntry("a.txt"));
                zos.write("hello".getBytes());
                zos.closeEntry();
            }
            try (JarFile jf = new JarFile(f.getPath())) {
                if (!f.getPath().equals(jf.getName())) {
                    throw new RuntimeException("unexpected name: " + jf.getName());
                }
                if (jf.getEntry("a.txt") == null) {
                    throw new RuntimeException("entry a.txt not found");
                }
                if (jf.size() != 1) {
                    throw new RuntimeException("unexpected size: " + jf.size());
                }
            }
        } finally {
            f.delete();
        }
    }
}
