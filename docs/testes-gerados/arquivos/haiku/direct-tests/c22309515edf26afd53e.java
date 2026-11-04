/*
 * @test
 * @summary Verifies public JarFile(String name, boolean verify) opens existing jars and rejects missing files
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class JarFileWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        File tmp = File.createTempFile("witup", ".jar");
        try {
            try (OutputStream os = new FileOutputStream(tmp);
                 JarOutputStream jos = new JarOutputStream(os)) {
                jos.putNextEntry(new JarEntry("a.txt"));
                jos.write("hello".getBytes("UTF-8"));
                jos.closeEntry();
            }

            try (JarFile jf = new JarFile(tmp.getPath(), true)) {
                check(tmp.getPath().equals(jf.getName()), "name mismatch with verify=true");
                check(jf.getEntry("a.txt") != null, "entry a.txt not found with verify=true");
                check(jf.getEntry("missing.txt") == null, "missing entry should be null");
                check(jf.size() == 1, "unexpected size with verify=true");
            }

            try (JarFile jf = new JarFile(tmp.getPath(), false)) {
                check(tmp.getPath().equals(jf.getName()), "name mismatch with verify=false");
                check(jf.getEntry("a.txt") != null, "entry a.txt not found with verify=false");
                check(jf.size() == 1, "unexpected size with verify=false");
            }
        } finally {
            tmp.delete();
        }

        File absent = File.createTempFile("witup-absent", ".jar");
        String absentName = absent.getPath();
        absent.delete();
        try {
            new JarFile(absentName, true);
            throw new RuntimeException("expected IOException for missing file");
        } catch (IOException expected) {
            // expected: the file does not exist
        }
    }
}
