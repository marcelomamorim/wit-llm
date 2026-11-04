/*
 * @test
 * @summary Behavioral tests for JarFile(String name, boolean verify) constructor delegation
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarFileWitupTest {

    public static void main(String[] args) throws Exception {
        testNullNameThrowsNullPointerException();
        testMissingFileThrowsIOException();
        testValidJarOpensWithVerifyTrue();
        testValidJarOpensWithVerifyFalse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullNameThrowsNullPointerException() {
        try {
            new JarFile((String) null, true);
        } catch (NullPointerException expected) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("Expected NullPointerException, got IOException", e);
        }
        throw new RuntimeException("Expected NullPointerException for null name");
    }

    static void testMissingFileThrowsIOException() throws Exception {
        File missing = new File("nonexistent-witup-" + System.nanoTime() + ".jar");
        try {
            new JarFile(missing.getPath(), true);
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("Expected IOException for missing file");
    }

    static File createJar() throws IOException {
        File f = File.createTempFile("witup", ".jar");
        Manifest m = new Manifest();
        m.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(f), m)) {
            jos.putNextEntry(new JarEntry("a.txt"));
            jos.write("hello".getBytes("UTF-8"));
            jos.closeEntry();
        }
        return f;
    }

    static void testValidJarOpensWithVerifyTrue() throws Exception {
        File f = createJar();
        try (JarFile jf = new JarFile(f.getPath(), true)) {
            check(f.getPath().equals(jf.getName()), "getName mismatch");
            check(jf.getManifest() != null, "manifest should be present");
            check(jf.getEntry("a.txt") != null, "entry a.txt should be present");
        } finally {
            f.delete();
        }
    }

    static void testValidJarOpensWithVerifyFalse() throws Exception {
        File f = createJar();
        try (JarFile jf = new JarFile(f.getPath(), false)) {
            check(f.getPath().equals(jf.getName()), "getName mismatch");
            check(jf.getEntry("a.txt") != null, "entry a.txt should be present");
        } finally {
            f.delete();
        }
    }
}
