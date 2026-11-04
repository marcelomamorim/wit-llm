/*
 * @test
 * @summary Witup-derived behavioral tests for JarFile(File, boolean) constructor
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarFileWitupTest {

    public static void main(String[] args) throws Exception {
        testNullFileThrowsNPE();
        testMissingFileThrowsIOException();
        testValidJarOpensWithVerify();
    }

    static void testNullFileThrowsNPE() {
        try {
            new JarFile((File) null, true);
            throw new RuntimeException("expected NullPointerException for null file");
        } catch (NullPointerException expected) {
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException", e);
        }
    }

    static void testMissingFileThrowsIOException() {
        File f = new File("no-such-jar-witup-" + System.nanoTime() + ".jar");
        if (f.exists()) {
            throw new RuntimeException("precondition failed: file exists");
        }
        try {
            new JarFile(f, true);
            throw new RuntimeException("expected IOException for missing file");
        } catch (IOException expected) {
        }
    }

    static void testValidJarOpensWithVerify() throws Exception {
        File f = File.createTempFile("witup", ".jar");
        try {
            Manifest m = new Manifest();
            m.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
            try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(f), m)) {
            }
            try (JarFile jf = new JarFile(f, true)) {
                if (!f.getPath().equals(jf.getName())) {
                    throw new RuntimeException("unexpected name: " + jf.getName());
                }
                if (jf.getManifest() == null) {
                    throw new RuntimeException("manifest expected but null");
                }
            }
        } finally {
            f.delete();
        }
    }
}
