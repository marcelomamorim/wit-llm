/*
 * @test
 * @summary Tests the public JarFile(String name) constructor for a valid jar, a missing file, a non-zip file and a null name
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

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        File jar = File.createTempFile("witup", ".jar");
        File txt = File.createTempFile("witup", ".txt");
        File missing = new File(jar.getPath() + ".missing");
        try {
            try (OutputStream os = new FileOutputStream(jar);
                 JarOutputStream jos = new JarOutputStream(os)) {
                jos.putNextEntry(new JarEntry("a.txt"));
                jos.write("hello".getBytes("UTF-8"));
                jos.closeEntry();
            }

            try (JarFile jf = new JarFile(jar.getPath())) {
                check(jar.getPath().equals(jf.getName()), "name mismatch");
                check(jf.getEntry("a.txt") != null, "entry a.txt not found");
                check(jf.size() == 1, "unexpected entry count: " + jf.size());
            }

            boolean missingFailed = false;
            try {
                new JarFile(missing.getPath());
            } catch (IOException expected) {
                missingFailed = true;
            }
            check(missingFailed, "missing file did not throw IOException");

            try (OutputStream os = new FileOutputStream(txt)) {
                os.write("not a zip archive".getBytes("UTF-8"));
            }
            boolean nonZipFailed = false;
            try {
                new JarFile(txt.getPath());
            } catch (IOException expected) {
                nonZipFailed = true;
            }
            check(nonZipFailed, "non-zip file did not throw IOException");

            boolean nullFailed = false;
            try {
                new JarFile((String) null);
            } catch (NullPointerException expected) {
                nullFailed = true;
            }
            check(nullFailed, "null name did not throw NullPointerException");
        } finally {
            jar.delete();
            txt.delete();
            missing.delete();
        }
    }
}
