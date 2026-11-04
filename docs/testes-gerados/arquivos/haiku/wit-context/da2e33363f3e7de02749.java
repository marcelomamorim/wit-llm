/*
 * @test
 * @summary Verifies that the JarFile constructor with Runtime.Version rejects a null version with NullPointerException and accepts valid versions on a valid (empty) zip/jar file
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;

public class JarFileWitupTest {

    public static void main(String[] args) throws Exception {
        File f = createEmptyZip();
        try {
            testNullVersionThrowsNPE(f);
            testValidVersionsAccepted(f);
        } finally {
            f.delete();
        }
    }

    static File createEmptyZip() throws IOException {
        File f = File.createTempFile("witup", ".jar");
        f.deleteOnExit();
        byte[] eocd = new byte[22];
        eocd[0] = 0x50;
        eocd[1] = 0x4b;
        eocd[2] = 0x05;
        eocd[3] = 0x06;
        try (FileOutputStream out = new FileOutputStream(f)) {
            out.write(eocd);
        }
        return f;
    }

    static void testNullVersionThrowsNPE(File f) throws IOException {
        boolean thrown = false;
        try (JarFile jf = new JarFile(f, true, ZipFile.OPEN_READ, null)) {
            // constructor must not complete normally
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null Runtime.Version");
        }
    }

    static void testValidVersionsAccepted(File f) throws IOException {
        try (JarFile jf = new JarFile(f, false, ZipFile.OPEN_READ, Runtime.Version.parse("8"))) {
        }
        try (JarFile jf = new JarFile(f, true, ZipFile.OPEN_READ, Runtime.Version.parse("11"))) {
        }
    }
}
