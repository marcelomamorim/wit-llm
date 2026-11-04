/*
 * @test
 * @summary Observable behavior of JarFile(File, boolean, int) for valid jar, invalid mode and missing file
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class JarFileWitupTest {

    public static void main(String[] args) throws Exception {
        File jar = File.createTempFile("witup", ".jar");
        try {
            Manifest mf = new Manifest();
            mf.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
            try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(jar), mf)) {
                jos.putNextEntry(new ZipEntry("hello.txt"));
                jos.write("hi".getBytes("UTF-8"));
                jos.closeEntry();
            }

            try (JarFile jf = new JarFile(jar, true, ZipFile.OPEN_READ)) {
                check(jf.getManifest() != null, "manifest should be present");
                check(jf.getEntry("hello.txt") != null, "entry hello.txt should be present");
                check(jf.getName().equals(jar.getPath()), "name should match file path");
                check(!jf.isMultiRelease(), "plain jar should not be multi-release");
            }

            boolean iae = false;
            try {
                new JarFile(jar, false, 0).close();
            } catch (IllegalArgumentException expected) {
                iae = true;
            }
            check(iae, "mode 0 without OPEN_READ should be rejected");

            File missing = new File(jar.getPath() + ".missing");
            boolean ioe = false;
            try {
                new JarFile(missing, false, ZipFile.OPEN_READ).close();
            } catch (IOException expected) {
                ioe = true;
            }
            check(ioe, "missing file should raise IOException");
        } finally {
            jar.delete();
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
