/*
 * @test
 * @summary Tests the JarFile(File, boolean, int, Runtime.Version) constructor observable behavior
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipFile;

public class JarFileWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static File createJar(String name) throws IOException {
        File f = new File(name);
        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(f))) {
            jos.putNextEntry(new JarEntry("a.txt"));
            jos.write("hello".getBytes("UTF-8"));
            jos.closeEntry();
        }
        return f;
    }

    static void testNullVersionThrowsNPE(File f) {
        try {
            new JarFile(f, true, ZipFile.OPEN_READ, null);
            throw new RuntimeException("NullPointerException expected for null version");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    static void testCurrentRuntimeVersion(File f) throws IOException {
        try (JarFile jf = new JarFile(f, true, ZipFile.OPEN_READ, Runtime.version())) {
            check(jf.getName().equals(f.getPath()), "name mismatch");
            check(jf.getEntry("a.txt") != null, "entry a.txt not found");
        }
    }

    static void testBaseVersionEight(File f) throws IOException {
        try (JarFile jf = new JarFile(f, false, ZipFile.OPEN_READ, Runtime.Version.parse("8"))) {
            check(jf.getEntry("a.txt") != null, "entry a.txt not found for version 8");
        }
    }

    static void testHigherVersionCanonicalized(File f) throws IOException {
        try (JarFile jf = new JarFile(f, true, ZipFile.OPEN_READ, Runtime.Version.parse("100"))) {
            check(jf.getEntry("a.txt") != null, "entry a.txt not found for version 100");
            check(jf.size() == 1, "unexpected entry count");
        }
    }

    public static void main(String[] args) throws Exception {
        File f = createJar("JarFileWitupTest.jar");
        testNullVersionThrowsNPE(f);
        testCurrentRuntimeVersion(f);
        testBaseVersionEight(f);
        testHigherVersionCanonicalized(f);
    }
}
