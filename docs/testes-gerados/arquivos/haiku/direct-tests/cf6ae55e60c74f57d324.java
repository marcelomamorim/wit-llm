/*
 * @test
 * @summary Tests the public JarFile(File) constructor on a valid jar and on a missing file
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class JarFileWitupTest {

    public static void main(String[] args) throws Exception {
        File jar = new File("JarFileWitupTest-valid.jar");
        try {
            try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(jar))) {
                jos.putNextEntry(new ZipEntry("a.txt"));
                jos.write("hello".getBytes("UTF-8"));
                jos.closeEntry();
            }
            try (JarFile jf = new JarFile(jar)) {
                check(jar.getPath().equals(jf.getName()), "getName should match file path");
                check(jf.getEntry("a.txt") != null, "entry a.txt should be present");
                check(jf.getManifest() == null, "jar without manifest should return null manifest");
                check(jf.size() == 1, "jar should contain exactly one entry");
            }
        } finally {
            jar.delete();
        }

        File missing = new File("JarFileWitupTest-missing.jar");
        boolean threw = false;
        try {
            new JarFile(missing);
        } catch (IOException e) {
            threw = true;
        }
        check(threw, "opening a missing file should throw IOException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
